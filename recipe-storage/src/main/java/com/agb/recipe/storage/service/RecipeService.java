package com.agb.recipe.storage.service;

import com.agb.recipe.storage.domain.Recipe;
import com.agb.recipe.storage.exception.RecipeNotFoundException;

public interface RecipeService
{
    public Recipe retireveMostRecentRecipe ();

    void storeRecipe (Recipe recipe) throws RecipeNotFoundException;

}
