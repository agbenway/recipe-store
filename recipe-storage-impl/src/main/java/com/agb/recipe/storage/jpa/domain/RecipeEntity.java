package com.agb.recipe.storage.jpa.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RECIPE")
public class RecipeEntity
{
    private Long id;
    private String title;
    private String description;
    private String link;
    private LocalDate addedDate;
    private String messageId;

    @Id
    @Column(name = "RECIPE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId ()
    {
        return id;
    }

    public void setId (Long id)
    {
        this.id = id;
    }

    @Column(name = "TITLE", nullable = false)
    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    @Column(name = "DESCRIPTION", nullable = true, length = 500)
    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    @Column(name = "LINK", nullable = false)
    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    @Column(name = "ADDED_DATE", nullable = false)
    public LocalDate getAddedDate ()
    {
        return addedDate;
    }

    public void setAddedDate (LocalDate addedDate)
    {
        this.addedDate = addedDate;
    }

    @Column(name = "MESSAGE_ID", nullable = true)
    public String getMessageId ()
    {
        return messageId;
    }

    public void setMessageId (String messageId)
    {
        this.messageId = messageId;
    }

}
