package com.agb.recipe.controller.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

public class Recipe
{
    private Long recipeId;
    @NotEmpty(message = "Please provide a URL")
    private String link;
    private String title;
    private String description;
    private LocalDate dateAdded;

    public Long getRecipeId ()
    {
        return recipeId;
    }

    public void setRecipeId (Long recipeId)
    {
        this.recipeId = recipeId;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
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

    public LocalDate getDateAdded ()
    {
        return dateAdded;
    }

    public void setDateAdded (LocalDate dateAdded)
    {
        this.dateAdded = dateAdded;
    }
}
