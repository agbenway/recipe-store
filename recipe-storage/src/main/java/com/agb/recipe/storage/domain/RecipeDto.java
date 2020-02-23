package com.agb.recipe.storage.domain;

import java.time.LocalDate;

public class RecipeDto
{
    private Long recipeId;
    private String title;
    private String description;
    private String link;
    private LocalDate messageDate;
    private String messageId;

    public Long getRecipeId ()
    {
        return recipeId;
    }

    public void setRecipeId (Long recipeId)
    {
        this.recipeId = recipeId;
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

    public LocalDate getMessageDate ()
    {
        return messageDate;
    }

    public void setMessageDate (LocalDate messageDate)
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
