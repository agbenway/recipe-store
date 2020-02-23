package com.agb.recipe.storage.exception;

public class RecipeNotFoundException extends Exception
{
    private static final long serialVersionUID = 1L;

    public RecipeNotFoundException(String message)
    {
        super(message);
    }

    public RecipeNotFoundException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

}
