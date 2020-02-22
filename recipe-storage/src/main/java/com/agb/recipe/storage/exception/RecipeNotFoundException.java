package com.agb.recipe.storage.exception;

public class RecipeNotFoundException extends Throwable
{
    private static final long serialVersionUID = 1L;

    public RecipeNotFoundException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

}
