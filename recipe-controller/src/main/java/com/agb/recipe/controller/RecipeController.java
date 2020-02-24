package com.agb.recipe.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agb.recipe.controller.converter.RecipeDtoToRecipeConverter;
import com.agb.recipe.controller.domain.Recipe;
import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.domain.RecipeSearchRequest;
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

    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    public @ResponseBody Recipe createRecipe (@Valid @RequestBody Recipe recipe) throws RecipeNotFoundException, DuplicateRecipeException
    {
        RecipeDto recipeDto = recipeConverter.convert(recipe, RecipeDto.class);

        recipeDto = recipeService.createRecipe(recipeDto);

        Recipe retval = recipeConverter.convert(recipeDto, Recipe.class);
        return retval;
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.PUT)
    public void updateRecipeFeedback (@Valid @RequestBody Recipe recipe)
    {
        // recipeService.updateRecipeFeedback(recipe);
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    public Page<Recipe> retrieveAllRecipes (@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size)
    {
        RecipeSearchRequest request = new RecipeSearchRequest(page, size);

        Page<RecipeDto> recipeDto = recipeService.retrieveRecipes(request);
        Page<Recipe> recipes = recipeDto.map(new RecipeDtoToRecipeConverter()::convert);

        return recipes;
    }

    @RequestMapping(value = "/recipes/{recipeId}", method = RequestMethod.GET)
    public @ResponseBody Recipe retrieveRecipe (@PathVariable(value = "recipeId") Long recipeId) throws RecipeNotFoundException
    {
        RecipeDto recipeDto = recipeService.retrieveRecipe(recipeId);
        Recipe recipe = recipeConverter.convert(recipeDto, Recipe.class);

        return recipe;
    }

}
