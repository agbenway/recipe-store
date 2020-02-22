package com.agb.recipe.reader.domain;

public class RecipeMessage
{
    private String messageId;
    private String messageDate;
    private String link;

    public String getMessageId ()
    {
        return messageId;
    }

    public void setMessageId (String messageId)
    {
        this.messageId = messageId;
    }

    public String getMessageDate ()
    {
        return messageDate;
    }

    public void setMessageDate (String messageDate)
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
