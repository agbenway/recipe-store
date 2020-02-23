package com.agb.recipe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.agb.recipe.controller.converter.RecipeDtoToRecipeConverter;
import com.agb.recipe.controller.converter.RecipeToRecipeDtoConverter;
import com.agb.recipe.storage.converter.RecipeEntityToRecipeDtoConverter;

@Configuration
@ComponentScan
@EnableScheduling
public class RecipeConfiguration
{
    @Bean
    public ConversionService recipeConverter ()
    {
        DefaultConversionService retval = new DefaultConversionService();

        retval.addConverter(new RecipeToRecipeDtoConverter());
        retval.addConverter(new RecipeDtoToRecipeConverter());
        retval.addConverter(new RecipeEntityToRecipeDtoConverter());

        return retval;
    }
}
