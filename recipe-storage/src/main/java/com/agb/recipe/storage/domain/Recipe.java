package com.agb.recipe.storage.domain;

import java.util.UUID;

public class Recipe
{
    private UUID recipeGuid;
    private String messageId;
    private String messageDate;
    private String link;

    public UUID getRecipeGuid ()
    {
        return recipeGuid;
    }

    public void setRecipeGuid (UUID recipeGuid)
    {
        this.recipeGuid = recipeGuid;
    }

    public String getMessageId ()
    {
        return messageId;
    }

    public void setMessageId (String messageId)
    {
        this.messageId = messageId;
    }

    public String getMessageDate ()
    {
        return messageDate;
    }

    public void setMessageDate (String messageDate)
    {
        this.messageDate = messageDate;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

}
