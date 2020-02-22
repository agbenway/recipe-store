package com.agb.recipe.reader.service;

import java.util.List;

import com.agb.recipe.reader.domain.QueryParameter;
import com.agb.recipe.reader.domain.RecipeMessage;
import com.agb.recipe.reader.exception.MessageNotFoundException;

public interface ReaderService
{
    public List<RecipeMessage> retrieveRecipeMessages (QueryParameter messageQueryParmeters) throws MessageNotFoundException;

}
