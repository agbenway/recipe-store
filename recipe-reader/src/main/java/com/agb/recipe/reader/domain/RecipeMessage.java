package com.agb.recipe.reader.domain;

import java.time.LocalDate;

public class RecipeMessage
{
    private String messageId;
    private LocalDate messageDate;
    private String link;

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

    public void setMessageDate (LocalDate localDate)
    {
        this.messageDate = localDate;
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
