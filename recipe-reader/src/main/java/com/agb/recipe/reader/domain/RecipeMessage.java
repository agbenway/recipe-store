package com.agb.recipe.reader.domain;

import java.time.LocalDateTime;

public class RecipeMessage
{
    private String messageId;
    private LocalDateTime messageDate;
    private String link;

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

    public void setMessageDate (LocalDateTime dateTime)
    {
        this.messageDate = dateTime;
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
