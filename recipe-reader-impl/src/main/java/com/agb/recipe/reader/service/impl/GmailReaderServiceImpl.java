package com.agb.recipe.reader.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agb.recipe.reader.domain.QueryParameter;
import com.agb.recipe.reader.domain.RecipeMessage;
import com.agb.recipe.reader.exception.CommunicationException;
import com.agb.recipe.reader.exception.MessageNotFoundException;
import com.agb.recipe.reader.exception.ParserException;
import com.agb.recipe.reader.gmail.GmailConnection;
import com.agb.recipe.reader.service.ReaderService;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;

@Service(GmailReaderServiceImpl.NAME)
public class GmailReaderServiceImpl implements ReaderService
{
    private static Logger log = LoggerFactory.getLogger(GmailReaderServiceImpl.class);

    public static final String NAME = "gmailReaderService";

    private static final String GMAIL_USER_ID = "me";
    private static final String URL_REG_EX = "((https?:\\/\\/)?(www[^\\.]?)\\.([\\w\\d]+)\\.(com|org|net|us|uk|edu)(\\/[^ ]*)?)";

    @Autowired
    public GmailConnection gmailConnection;

    GmailReaderServiceImpl(GmailConnection gmailConnection)
    {
        this.gmailConnection = gmailConnection;
    }

    @Override
    public List<RecipeMessage> retrieveRecipeMessages (QueryParameter queryParmeter)
            throws MessageNotFoundException
    {
        Gmail service = gmailConnection.initalizeGmailService();

        ListMessagesResponse messageResponse = null;
        try
        {
            String query = createQuery(queryParmeter);
            log.debug("Gmail query '{}'", query);

            log.debug("Retrieving list of messages from Gmail.");
            messageResponse = service.users().messages().list(GMAIL_USER_ID).setQ(query).execute();
            log.debug("Retrieved list of messages from Gmail.");
        }
        catch (IOException ioEx)
        {
            throw new CommunicationException("Error communicating with Gmail", ioEx);
        }
        List<Message> messages = messageResponse.getMessages();

        if (messages == null || messages.isEmpty())
        {
            throw new MessageNotFoundException("No new messages found");
        }

        log.debug("Streaming through the list of messages to find recipe links.");
        Map<String, Message> messageLinks = messages.stream().map(x -> {
            Message message = null;
            try
            {
                message = service.users().messages().get(GMAIL_USER_ID, x.getId()).execute();
            }
            catch (IOException ioEx)
            {
                throw new CommunicationException("Error communicating with Gmail", ioEx);
            }
            return message;
        }).filter(s -> s != null && s.getSnippet().contains("https://")).collect(Collectors.toMap(Message::getId, Function.identity()));
        log.debug("Done streaming through the list of messages.");

        if (messageLinks.isEmpty())
        {
            throw new MessageNotFoundException("No new recipes found in messages.");
        }

        List<RecipeMessage> recipeMessages = createRecipeMessagesFromEmails(messageLinks);

        return recipeMessages;
    }

    private String createQuery (QueryParameter queryParmeter)
    {
        StringBuilder query = new StringBuilder();

        String fromAddress = queryParmeter.getFromAddress();
        if (fromAddress != null)
        {
            query.append("from:").append(queryParmeter.getFromAddress());
        }
        LocalDateTime lastRetrievedDate = queryParmeter.getLastRetrievedDate();
        if (queryParmeter != null)
        {
            query.append("after:")
                    .append(lastRetrievedDate.getYear())
                    .append("/")
                    .append(lastRetrievedDate.getMonthValue())
                    .append("/")
                    .append(lastRetrievedDate.getDayOfMonth());
        }

        return query.toString();
    }

    private List<RecipeMessage> createRecipeMessagesFromEmails (Map<String, Message> messageLinks)
    {
        List<RecipeMessage> recipeMessages = new ArrayList<>();
        for (Map.Entry<String, Message> entry : messageLinks.entrySet())
        {
            String link = findUrl(entry.getValue());
            if (link != null)
            {
                RecipeMessage recipe = new RecipeMessage();
                recipe.setMessageId(entry.getValue().getId());

                LocalDateTime dateTime = parseEmailDateTime(entry.getValue().getPayload().getHeaders());
                recipe.setMessageDate(dateTime);

                recipe.setLink(link);
                recipeMessages.add(recipe);
            }
        }
        return recipeMessages;
    }

    private LocalDateTime parseEmailDateTime (List<MessagePartHeader> headers)
    {
        LocalDateTime dateTime = null;
        for (MessagePartHeader header : headers)
        {
            if ("Date".equals(header.getName()))
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM yyyy HH:mm:ss Z");
                dateTime = LocalDateTime.parse(header.getValue(), formatter);
                break;
            }
        }

        return dateTime;
    }

    private static String findUrl (Message message)
    {
        String url = null;
        Pattern p = Pattern.compile(URL_REG_EX);

        for (MessagePart part : message.getPayload().getParts())
        {
            String body = null;
            try
            {
                body = StringUtils.newStringUtf8(Base64.decodeBase64(part.toPrettyString()));
                body = body.replace("\r", " ");
                body = body.replace("\n", " ");
            }
            catch (IOException ioEx)
            {
                throw new ParserException("Error retrieving link from messageId: " + message.getId(), ioEx);
            }

            Matcher matcher = p.matcher(body);

            if (matcher.find())
            {
                url = matcher.group(1);
                log.debug("Message Id: {}; Recipe URL: {}", message.getId(), url);
                break;
            }
        }

        return url;
    }

}