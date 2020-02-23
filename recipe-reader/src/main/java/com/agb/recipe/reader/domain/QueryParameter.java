package com.agb.recipe.reader.domain;

import java.time.LocalDate;

public class QueryParameter
{
    private LocalDate lastRetrievedDate;
    private String searchAddress;

    public QueryParameter(LocalDate lastRetrievedDate)
    {
        this.lastRetrievedDate = lastRetrievedDate;
    }

    public QueryParameter(LocalDate lastRetrievedDate, String searchAddress)
    {
        this.lastRetrievedDate = lastRetrievedDate;
        this.searchAddress = searchAddress;
    }

    public LocalDate getLastRetrievedDate ()
    {
        return lastRetrievedDate;
    }

    public void setLastRetrievedDate (LocalDate lastRetrievedDate)
    {
        this.lastRetrievedDate = lastRetrievedDate;
    }

    public String getSearchAddress ()
    {
        return searchAddress;
    }

    public void setSearchAddress (String searchAddress)
    {
        this.searchAddress = searchAddress;
    }
}
