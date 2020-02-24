package com.agb.recipe.storage.domain;

import org.springframework.data.domain.Sort.Direction;

public class RecipeSearchRequest
{
    private int page;
    private int size;
    private Direction sortDirection;
    private String sortBy;

    public RecipeSearchRequest(int page, int size)
    {
        this.page = page;
        this.size = size;
        this.sortDirection = Direction.ASC;
        this.sortBy = "id";
    }

    public RecipeSearchRequest(int page, int size, Direction sortDirection, String sortBy)
    {
        this.page = page;
        this.size = size;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }

    public int getPage ()
    {
        return page;
    }

    public int getSize ()
    {
        return size;
    }

    public Direction getSortDirection ()
    {
        return sortDirection;
    }

    public String getSortBy ()
    {
        return sortBy;
    }

}
