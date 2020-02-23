package com.agb.recipe.controller.converter;

import org.springframework.core.convert.converter.Converter;

import com.agb.recipe.controller.domain.Recipe;
import com.agb.recipe.storage.domain.RecipeDto;

public class RecipeDtoToRecipeConverter implements Converter<RecipeDto, Recipe>
{

    @Override
    public Recipe convert (RecipeDto source)
    {
        Recipe retval = null;

        if (source != null)
        {
            retval = new Recipe();
            retval.setRecipeId(source.getRecipeId());
            retval.setTitle(source.getTitle());
            retval.setDescription(source.getDescription());
            retval.setLink(source.getLink());
            retval.setDateAdded(source.getMessageDate());
        }

        return retval;
    }

}
