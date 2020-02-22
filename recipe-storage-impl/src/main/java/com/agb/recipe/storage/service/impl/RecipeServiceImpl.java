package com.agb.recipe.storage.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.agb.recipe.storage.domain.Recipe;
import com.agb.recipe.storage.exception.RecipeNotFoundException;
import com.agb.recipe.storage.jpa.domain.RecipeEntity;
import com.agb.recipe.storage.jpa.repository.RecipeRepository;
import com.agb.recipe.storage.service.RecipeService;

@Service(RecipeServiceImpl.NAME)
public class RecipeServiceImpl implements RecipeService
{
    public static final String NAME = "recipeService";

    @Autowired
    public ConversionService recipeConverter;

    @Autowired
    public RecipeRepository recipeRepository;

    RecipeServiceImpl(RecipeRepository recipeRepository)
    {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe retireveMostRecentRecipe ()
    {
        RecipeEntity recipeEntity = recipeRepository.findFirstByOrderByEmailDateTimeDesc();
        Recipe recipe = recipeConverter.convert(recipeEntity, Recipe.class);

        return recipe;
    }

    @Override
    public void storeRecipe (Recipe recipe) throws RecipeNotFoundException
    {
        if (recipe != null && StringUtils.isNotBlank(recipe.getLink()))
        {
            Document doc = null;
            try
            {
                doc = Jsoup.connect(recipe.getLink()).get();
            }
            catch (IOException ex)
            {
                throw new RecipeNotFoundException("Recipe is no longer avaliable.", ex);
            }

            RecipeEntity entity = new RecipeEntity();
            entity.setGuid(UUID.randomUUID());
            entity.setName(doc.title());
            entity.setDescription(getMetaTag(doc, "description"));
            entity.setLink(recipe.getLink());
            entity.setEmailDateTime(recipe.getMessageDate());
            entity = recipeRepository.saveAndFlush(entity);

            System.out.println("RecipeID: " + entity.getId());
        }

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
