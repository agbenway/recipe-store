package com.agb.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.agb.recipe.reader.RederConfiguration;

@SpringBootApplication
@Import({RederConfiguration.class})
public class RecipeApplication
{
    public static void main (String[] args)
    {
        SpringApplication.run(RecipeApplication.class, args);
    }

}
