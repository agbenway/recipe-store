package com.agb.recipe.storage.jpa.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converts;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RECIPE")
// @Converts(value = {
// @Convert(attributeName = "emailDate", converter = LocalDateAttributeConverter.class)
// })
public class RecipeEntity
{
    private Long id;
    private String title;
    private String description;
    private String link;
    private LocalDate emailDate;

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

    @Column(name = "EMAIL_DATE", nullable = false)
    public LocalDate getEmailDate ()
    {
        return emailDate;
    }

    public void setEmailDate (LocalDate emailDate)
    {
        this.emailDate = emailDate;
    }

}
