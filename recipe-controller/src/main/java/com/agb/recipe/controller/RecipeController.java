package com.agb.recipe.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agb.recipe.controller.domain.Recipe;
import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.exception.DuplicateRecipeException;
import com.agb.recipe.storage.exception.RecipeNotFoundException;
import com.agb.recipe.storage.service.RecipeService;

@RestController
public class RecipeController
{
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ConversionService recipeConverter;

    // TODO: Search recipes (Pageable)

    @RequestMapping(value = "/recipe", method = RequestMethod.POST)
    public void createRecipe (@Valid @RequestBody Recipe recipe) throws RecipeNotFoundException, DuplicateRecipeException
    {
        RecipeDto recipeDto = recipeConverter.convert(recipe, RecipeDto.class);
        recipeService.createRecipe(recipeDto);
    }

    @RequestMapping(value = "/recipe", method = RequestMethod.PUT)
    public void updateRecipe (@Valid @RequestBody Recipe recipe)
    {
        // recipeService.updateRecipe(recipe);
    }

    @RequestMapping(value = "/recipe/{recipeId}", method = RequestMethod.GET)
    public @ResponseBody Recipe retrieveRecipe (@PathVariable(value = "recipeId") Long recipeId) throws RecipeNotFoundException
    {
        RecipeDto recipeDto = recipeService.retrieveRecipe(recipeId);
        Recipe recipe = recipeConverter.convert(recipeDto, Recipe.class);

        return recipe;
    }

}
