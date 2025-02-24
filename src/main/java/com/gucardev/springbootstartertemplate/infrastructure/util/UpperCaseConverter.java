package com.gucardev.springbootstartertemplate.infrastructure.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;
import java.util.Optional;

//  @Convert(converter = UpperCaseConverter.class)

@Converter
public class UpperCaseConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String s) {
        return Optional.ofNullable(s).map(x -> x.toUpperCase(Locale.ROOT)).orElse(null);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return Optional.ofNullable(s).map(x -> x.toUpperCase(Locale.ROOT)).orElse(null);
    }
}
