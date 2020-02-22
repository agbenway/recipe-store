package com.agb.recipe.reader.exception;

public class CommunicationException extends RuntimeException
{
    private static final long serialVersionUID = -4809274720516611501L;

    public CommunicationException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
