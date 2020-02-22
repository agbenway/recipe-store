package com.agb.recipe.reader.exception;

public class ParserException extends RuntimeException
{
    private static final long serialVersionUID = -4809274720516611501L;

    public ParserException(String fullString, Throwable throwable)
    {
        super(fullString, throwable);
    }
}
