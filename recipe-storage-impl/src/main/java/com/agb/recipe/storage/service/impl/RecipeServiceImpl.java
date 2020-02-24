package com.agb.recipe.storage.service.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.agb.recipe.storage.converter.RecipeEntityToRecipeDtoConverter;
import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.domain.RecipeSearchRequest;
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
        RecipeEntity recipeEntity = recipeRepository.findFirstByOrderByAddedDateDesc();
        RecipeDto recipe = recipeConverter.convert(recipeEntity, RecipeDto.class);

        return recipe;
    }

    @Override
    public RecipeDto createRecipe (RecipeDto recipe) throws RecipeNotFoundException, DuplicateRecipeException
    {
        RecipeEntity entity = null;
        if (StringUtils.isNotBlank(recipe.getMessageId()))
        {
            if (recipeRepository.findByMessageId(recipe.getMessageId()).isPresent())
            {
                throw new DuplicateRecipeException("Recipe messageId already exists.");
            }
        }
        else if (recipeRepository.findByLink(recipe.getLink()).isPresent())
        {
            throw new DuplicateRecipeException("Recipe link already exists.");
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
        entity.setAddedDate(recipe.getMessageDate());
        entity.setMessageId(recipe.getMessageId());
        entity = recipeRepository.save(entity);

        log.debug("Created RecipeId: {}, Title: {}", entity.getId(), entity.getTitle());

        RecipeDto retval = recipeConverter.convert(entity, RecipeDto.class);

        return retval;
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

    @Override
    public Page<RecipeDto> retrieveRecipes (RecipeSearchRequest request)
    {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), request.getSortDirection(),
                request.getSortBy());

        Page<RecipeEntity> entities = recipeRepository.findAll(pageable);

        Page<RecipeDto> recipes = entities.map(new RecipeEntityToRecipeDtoConverter()::convert);

        return recipes;
    }
}
