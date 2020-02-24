package com.agb.recipe.storage.converter;

import org.springframework.core.convert.converter.Converter;

import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.jpa.domain.RecipeEntity;

public class RecipeEntityToRecipeDtoConverter implements Converter<RecipeEntity, RecipeDto>
{

    @Override
    public RecipeDto convert (RecipeEntity source)
    {
        RecipeDto retval = null;

        if (source != null)
        {
            retval = new RecipeDto();
            retval.setRecipeId(source.getId());
            retval.setTitle(source.getTitle());
            retval.setDescription(source.getDescription());
            retval.setLink(source.getLink());
            retval.setMessageDate(source.getAddedDate());
        }

        return retval;
    }

}
