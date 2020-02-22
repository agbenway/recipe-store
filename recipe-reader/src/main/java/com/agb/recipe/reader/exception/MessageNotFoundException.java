package com.agb.recipe.reader.exception;

public class MessageNotFoundException extends Exception
{
    private static final long serialVersionUID = -4809274720516611501L;

    public MessageNotFoundException(String message)
    {
        super(message);
    }
}
