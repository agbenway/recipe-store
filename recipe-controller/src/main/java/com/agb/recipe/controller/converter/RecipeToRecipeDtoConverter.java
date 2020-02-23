package com.agb.recipe.controller.converter;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

import com.agb.recipe.controller.domain.Recipe;
import com.agb.recipe.storage.domain.RecipeDto;

public class RecipeToRecipeDtoConverter implements Converter<Recipe, RecipeDto>
{

    @Override
    public RecipeDto convert (Recipe source)
    {
        RecipeDto retval = null;

        if (source != null)
        {
            retval = new RecipeDto();
            retval.setTitle(source.getTitle());
            retval.setDescription(source.getDescription());
            retval.setLink(source.getLink());
            retval.setMessageDate(LocalDate.now());
        }

        return retval;
    }

}
