package com.agb.recipe.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.agb.recipe.reader.domain.QueryParameter;
import com.agb.recipe.reader.domain.RecipeMessage;
import com.agb.recipe.reader.exception.MessageNotFoundException;
import com.agb.recipe.reader.service.ReaderService;
import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.service.RecipeService;

@Component
public class RecipeInitalizer
{
    private static Logger log = LoggerFactory.getLogger(RecipeInitalizer.class);

    private static final String INITIAL_ADDRESS = "mikenewman3@gmail.com ";

    @Autowired
    public ReaderService gmailReaderService;

    @Autowired
    public RecipeService recipeService;

    RecipeInitalizer(ReaderService gmailReaderService, RecipeService recipeService)
    {
        this.gmailReaderService = gmailReaderService;
        this.recipeService = recipeService;
    }

    @PostConstruct
    public void initializeRecipes ()
    {
        RecipeDto recipe = recipeService.retireveMostRecentRecipe();
        LocalDate lastRetrievedDate = null;

        if (recipe != null)
        {
            lastRetrievedDate = recipe.getMessageDate();
        }

        List<RecipeMessage> recipeMessages = new ArrayList<>();
        try
        {
            QueryParameter queryParmeter = new QueryParameter(lastRetrievedDate, INITIAL_ADDRESS);
            recipeMessages = gmailReaderService.retrieveRecipeMessages(queryParmeter);
        }
        catch (MessageNotFoundException ex)
        {
            log.info("Exception caught in initializing recipes: {}", ex.getMessage());
        }

        storeRecipes(recipeMessages);
    }

    @Scheduled(fixedRate = 3600000) // 1 hour
    public void scheduledRecipeCheck ()
    {
        List<RecipeMessage> recipeMessages = new ArrayList<>();
        try
        {
            QueryParameter queryParmeter = new QueryParameter(LocalDate.now().minusDays(1));
            recipeMessages = gmailReaderService.retrieveRecipeMessages(queryParmeter);
        }
        catch (MessageNotFoundException ex)
        {
            log.info("Exception caught in recipe check: {}", ex.getMessage());
        }

        storeRecipes(recipeMessages);
    }

    private void storeRecipes (List<RecipeMessage> recipeMessages)
    {
        for (RecipeMessage recipeMessage : recipeMessages)
        {
            RecipeDto recipeDto = new RecipeDto();
            recipeDto.setLink(recipeMessage.getLink());
            recipeDto.setMessageId(recipeMessage.getMessageId());
            recipeDto.setMessageDate(recipeMessage.getMessageDate());

            try
            {
                recipeService.createRecipe(recipeDto);
            }
            catch (Exception ex)
            {
                log.info("Exception caught in initializing recipes: {}", ex.getMessage());
            }
        }
    }

}
