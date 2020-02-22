package com.agb.recipe.storage.converter;

import org.springframework.core.convert.converter.Converter;

import com.agb.recipe.storage.domain.Recipe;
import com.agb.recipe.storage.jpa.domain.RecipeEntity;

public class RecipeEntityToRecipeConverter implements Converter<RecipeEntity, Recipe>
{

    @Override
    public Recipe convert (RecipeEntity source)
    {
        Recipe retval = null;

        if (source != null)
        {
            retval = new Recipe();
            retval.setRecipeGuid(source.getGuid());
            retval.setTitle(source.getName());
            retval.setDescription(source.getDescription());
            retval.setLink(source.getLink());
            retval.setMessageDate(source.getEmailDateTime());
        }

        return retval;
    }

}
