package com.agb.recipe.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import com.agb.recipe.storage.converter.RecipeEntityToRecipeConverter;

@Configuration
public class RecipeStorageConfiguration
{
    @Bean
    public ConversionService recipeConverter ()
    {
        DefaultConversionService retval = new DefaultConversionService();

        retval.addConverter(new RecipeEntityToRecipeConverter());

        return retval;
    }
}
