package com.agb.recipe.storage.service;

import org.springframework.data.domain.Page;

import com.agb.recipe.storage.domain.RecipeDto;
import com.agb.recipe.storage.domain.RecipeSearchRequest;
import com.agb.recipe.storage.exception.DuplicateRecipeException;
import com.agb.recipe.storage.exception.RecipeNotFoundException;

public interface RecipeService
{
    public RecipeDto retireveMostRecentRecipe ();

    public RecipeDto createRecipe (RecipeDto recipe) throws RecipeNotFoundException, DuplicateRecipeException;

    public RecipeDto retrieveRecipe (Long recipeId) throws RecipeNotFoundException;

    public Page<RecipeDto> retrieveRecipes (RecipeSearchRequest request);
}
