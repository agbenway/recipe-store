package com.agb.recipe.storage.exception;

public class DuplicateRecipeException extends Exception
{
    private static final long serialVersionUID = 1L;

    public DuplicateRecipeException(String message)
    {
        super(message);
    }
}
