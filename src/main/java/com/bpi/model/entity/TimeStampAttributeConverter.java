package com.bpi.model.entity;

import jakarta.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class TimeStampAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (Optional.ofNullable(localDateTime).isPresent()) {
            return Timestamp.valueOf(localDateTime);
        }
        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        if (Optional.ofNullable(timestamp).isPresent()) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }
}
