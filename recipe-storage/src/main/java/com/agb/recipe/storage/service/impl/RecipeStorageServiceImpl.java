package com.agb.recipe.storage.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agb.recipe.storage.domain.Recipe;
import com.agb.recipe.storage.exception.RecipeNotFoundException;
import com.agb.recipe.storage.jpa.domain.RecipeEntity;
import com.agb.recipe.storage.jpa.repository.RecipeRepository;

@Service(RecipeStorageServiceImpl.NAME)
public class RecipeStorageServiceImpl
{
    public static final String NAME = "recipeStorageServiceImpl";

    @Autowired
    public RecipeRepository recipeRepository;

    RecipeStorageServiceImpl(RecipeRepository recipeRepository)
    {
        this.recipeRepository = recipeRepository;
    }

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
                throw new RecipeNotFoundException("Recipe is no longer there.", ex);
            }

            RecipeEntity entity = new RecipeEntity();
            entity.setGuid(UUID.randomUUID());
            entity.setName(doc.title());
            entity.setDescription(getMetaTag(doc, "description"));
            entity.setLink(recipe.getLink());
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
