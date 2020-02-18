package com.agb.recipe.reader.service;

import java.util.List;

import com.agb.recipe.reader.domain.RecipeMessage;

public interface ReaderService
{
    public List<RecipeMessage> retrieveRecipeMessages ();

}
