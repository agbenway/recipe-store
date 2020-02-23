package com.agb.recipe.storage.service.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.exception.DuplicateRecipeException;
import com.agb.recipe.storage.exception.RecipeNotFoundException;
import com.agb.recipe.storage.jpa.domain.RecipeEntity;
import com.agb.recipe.storage.jpa.repository.RecipeRepository;
import com.agb.recipe.storage.service.RecipeService;

@Service(RecipeServiceImpl.NAME)
public class RecipeServiceImpl implements RecipeService
{
    public static final String NAME = "recipeService";

    private static Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    public ConversionService recipeConverter;

    @Autowired
    public RecipeRepository recipeRepository;

    RecipeServiceImpl(RecipeRepository recipeRepository)
    {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipeDto retireveMostRecentRecipe ()
    {
        RecipeEntity recipeEntity = recipeRepository.findFirstByOrderByEmailDateDesc();
        RecipeDto recipe = recipeConverter.convert(recipeEntity, RecipeDto.class);

        return recipe;
    }

    @Override
    public void createRecipe (RecipeDto recipe) throws RecipeNotFoundException, DuplicateRecipeException
    {
        RecipeEntity entity = recipeRepository.findByLink(recipe.getLink());
        if (entity != null)
        {
            throw new DuplicateRecipeException("Recipe already exists.");
        }

        Document doc = null;
        try
        {
            doc = Jsoup.connect(recipe.getLink()).get();
        }
        catch (IOException ex)
        {
            throw new RecipeNotFoundException("Recipe is no longer avaliable.", ex);
        }

        entity = new RecipeEntity();
        entity.setTitle(doc.title());
        entity.setDescription(getMetaTag(doc, "description"));
        entity.setLink(recipe.getLink());
        entity.setEmailDate(recipe.getMessageDate());
        entity = recipeRepository.saveAndFlush(entity);

        log.debug("Created RecipeId: {}, Title: {}", entity.getId(), entity.getTitle());
    }

    @Override
    public RecipeDto retrieveRecipe (Long recipeId) throws RecipeNotFoundException
    {
        RecipeEntity recipeEntity = recipeRepository.findById(recipeId)
                .orElseThrow( () -> new RecipeNotFoundException("Recipe with ID " + recipeId + " not found."));

        RecipeDto recipe = recipeConverter.convert(recipeEntity, RecipeDto.class);

        return recipe;
    }

    private static String getMetaTag (Document document, String attr)
    {
        Elements elements = document.select("meta[name=" + attr + "]");
        for (Element element : elements)
        {
            final String s = element.attr("content");
            if (s != null)
                return s;
        }
        elements = document.select("meta[property=" + attr + "]");
        for (Element element : elements)
        {
            final String s = element.attr("content");
            if (s != null)
                return s;
        }
        return null;
    }
}
