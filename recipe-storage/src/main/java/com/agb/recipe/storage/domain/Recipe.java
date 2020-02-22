package com.agb.recipe.storage.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Recipe
{
    private UUID recipeGuid;
    private String title;
    private String description;
    private String link;
    private LocalDateTime messageDate;
    private String messageId;

    public UUID getRecipeGuid ()
    {
        return recipeGuid;
    }

    public void setRecipeGuid (UUID recipeGuid)
    {
        this.recipeGuid = recipeGuid;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getMessageId ()
    {
        return messageId;
    }

    public void setMessageId (String messageId)
    {
        this.messageId = messageId;
    }

    public LocalDateTime getMessageDate ()
    {
        return messageDate;
    }

    public void setMessageDate (LocalDateTime messageDate)
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
