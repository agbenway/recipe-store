package com.agb.recipe.reader.domain;

import java.time.LocalDateTime;

public class QueryParameter
{
    private LocalDateTime lastRetrievedDate;
    private String fromAddress;

    public QueryParameter(LocalDateTime lastRetrievedDate)
    {
        this.lastRetrievedDate = lastRetrievedDate;
    }

    public QueryParameter(LocalDateTime lastRetrievedDate, String fromAddress)
    {
        this.lastRetrievedDate = lastRetrievedDate;
        this.fromAddress = fromAddress;
    }

    public LocalDateTime getLastRetrievedDate ()
    {
        return lastRetrievedDate;
    }

    public void setLastRetrievedDate (LocalDateTime lastRetrievedDate)
    {
        this.lastRetrievedDate = lastRetrievedDate;
    }

    public String getFromAddress ()
    {
        return fromAddress;
    }

    public void setFromAddress (String fromAddress)
    {
        this.fromAddress = fromAddress;
    }
}
