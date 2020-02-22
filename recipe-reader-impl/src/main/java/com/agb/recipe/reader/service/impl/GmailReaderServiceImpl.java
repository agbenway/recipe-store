package com.agb.recipe.reader.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agb.recipe.reader.domain.RecipeMessage;
import com.agb.recipe.reader.gmail.GmailConnection;
import com.agb.recipe.reader.service.ReaderService;
import com.agb.recipe.storage.domain.Recipe;
import com.agb.recipe.storage.exception.RecipeNotFoundException;
import com.agb.recipe.storage.service.impl.RecipeStorageServiceImpl;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;

// TODO: Search terms, from, subject contains, null subject
@Service(GmailReaderServiceImpl.NAME)
public class GmailReaderServiceImpl implements ReaderService
{
    public static final String NAME = "gmailReaderService";

    @Autowired
    public GmailConnection gmailConnection;

    @Autowired
    public RecipeStorageServiceImpl recipeStorageServiceImpl;

    private static final String URL_REG_EX = "((https?:\\/\\/)?(www[^\\.]?)\\.([\\w\\d]+)\\.(com|org|net|us|uk|edu)(\\/[^ ]*)?)";

    GmailReaderServiceImpl(GmailConnection gmailConnection, RecipeStorageServiceImpl recipeStorageServiceImpl)
    {
        this.gmailConnection = gmailConnection;
        this.recipeStorageServiceImpl = recipeStorageServiceImpl;
    }

    @PostConstruct
    @Override
    public List<RecipeMessage> retrieveRecipeMessages ()
    {
        List<RecipeMessage> recipeMessages = new ArrayList<>();
        Gmail service = gmailConnection.initalizeGmailService();

        String user = "me";
        ListMessagesResponse messageResponse = null;
        try
        {
            messageResponse = service.users().messages().list(user).setQ("mikenewman3@gmail.com").execute();
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        List<Message> messages = messageResponse.getMessages();

        if (messages.isEmpty())
        {
            System.out.println("No messages found.");
        }
        else
        {
            Map<String, Message> messageLinks = messages.stream().map(x -> {
                Message message = null;
                try
                {
                    message = service.users().messages().get(user, x.getId()).execute();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return message;
            }).filter(s -> s != null && s.getSnippet().contains("https://")).collect(Collectors.toMap(Message::getId, Function.identity()));

            if (!messageLinks.isEmpty())
            {
                for (Map.Entry<String, Message> entry : messageLinks.entrySet())
                {
                    String link = findUrl(entry.getValue());
                    if (link != null)
                    {
                        RecipeMessage recipe = new RecipeMessage();
                        recipe.setMessageId(entry.getValue().getId());
                        // recipe.setMessageDate(entry.getValue().get());
                        recipe.setLink(link);
                        recipeMessages.add(recipe);

                        Recipe recipeDto = new Recipe();
                        recipeDto.setLink(link);
                        recipeDto.setMessageId(entry.getValue().getId());

                        try
                        {
                            recipeStorageServiceImpl.storeRecipe(recipeDto);
                        }
                        catch (RecipeNotFoundException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return recipeMessages;
    }

    private static String findUrl (Message message)
    {
        String url = null;
        Pattern p = Pattern.compile(URL_REG_EX);   // the pattern to search for

        for (MessagePart part : message.getPayload().getParts())
        {
            String body = null;
            try
            {
                body = StringUtils.newStringUtf8(Base64.decodeBase64(part.toPrettyString()));
                body = body.replace("\r", " ");
                body = body.replace("\n", " ");
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Matcher matcher = p.matcher(body);

            if (matcher.find())
            {
                url = matcher.group(1);
                // url.replace('/n', '');
                System.out.format("'%s'\n", url);
                break;
            }
        }

        return url;
    }

}