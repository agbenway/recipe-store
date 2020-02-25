package com.agb.recipe.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.agb.recipe.controller.converter.RecipeDtoToRecipeConverter;
import com.agb.recipe.controller.converter.RecipeToRecipeDtoConverter;
import com.agb.recipe.controller.mock.MockRecipeService;
import com.agb.recipe.storage.service.RecipeService;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan
public class RecipeControllerTestConfiguration
{
    @Bean
    public RecipeService recipeService ()
    {
        return new MockRecipeService();
    }

    @Bean
    public ConversionService recipeConverter ()
    {
        DefaultConversionService retval = new DefaultConversionService();

        retval.addConverter(new RecipeToRecipeDtoConverter());
        retval.addConverter(new RecipeDtoToRecipeConverter());

        return retval;
    }
}
