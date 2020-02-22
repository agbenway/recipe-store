package com.agb.recipe.storage.jpa.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converts;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.agb.recipe.storage.jpa.domain.converter.UUIDConverter;

@Entity
@Table(name = "RECIPE")
@Converts(value = {
        @Convert(attributeName = "uuid", converter = UUIDConverter.class)
})
public class RecipeEntity
{
    private Long id;
    private UUID guid;
    private String name;
    private String description;
    private String link;

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

    @Column(name = "RECIPE_GUID", nullable = false)
    public UUID getGuid ()
    {
        return guid;
    }

    public void setGuid (UUID guid)
    {
        this.guid = guid;
    }

    @Column(name = "NAME", nullable = false)
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", nullable = true)
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

}
