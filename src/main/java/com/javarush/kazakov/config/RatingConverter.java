package com.javarush.kazakov.config;

import com.javarush.kazakov.entity.misc.Rating;
import jakarta.persistence.AttributeConverter;

public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name().replace('_', '-');
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Rating.valueOf(dbData.replace('-', '_'));
    }
}

