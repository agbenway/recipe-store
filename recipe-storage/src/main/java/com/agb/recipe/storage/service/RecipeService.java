package com.agb.recipe.storage.service;

import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.exception.DuplicateRecipeException;
import com.agb.recipe.storage.exception.RecipeNotFoundException;

public interface RecipeService
{
    public RecipeDto retireveMostRecentRecipe ();

    public void createRecipe (RecipeDto recipe) throws RecipeNotFoundException, DuplicateRecipeException;

    public RecipeDto retrieveRecipe (Long recipeId) throws RecipeNotFoundException;
}
