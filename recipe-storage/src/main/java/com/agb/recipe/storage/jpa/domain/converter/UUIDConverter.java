package com.agb.recipe.storage.jpa.domain.converter;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class UUIDConverter implements AttributeConverter<UUID, String>
{

    @Override
    public String convertToDatabaseColumn (UUID value)
    {
        String uuid = null;
        if (value != null)
        {
            uuid = value.toString();
        }
        return uuid;
    }

    @Override
    public UUID convertToEntityAttribute (String value)
    {
        UUID uuid = null;
        if (value != null)
        {
            uuid = UUID.fromString(value);
        }
        return uuid;
    }

}
