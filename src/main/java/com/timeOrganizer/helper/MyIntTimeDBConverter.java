package com.timeOrganizer.helper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MyIntTimeDBConverter implements AttributeConverter<MyIntTime, Long> {
    @Override
    public Long convertToDatabaseColumn(MyIntTime attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getHours() * 3600L +
                attribute.getMinutes() * 60L +
                attribute.getSeconds();
    }
    @Override
    public MyIntTime convertToEntityAttribute(Long dbData) {
        if (dbData == null) {
            return null;
        }
        int hours = (int) (dbData / 3600);
        int minutes = (int) ((dbData % 3600) / 60);
        int seconds = (int) (dbData % 60);
        return new MyIntTime(hours, minutes, seconds);
    }
}