package com.gucardev.springbootstartertemplate.infrastructure.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;

//  @Convert(converter = JsonConverter.class)

@Converter
public class JsonColumnConverterUsingGson implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object o) {
        return new Gson().toJson(o);
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        return new Gson().fromJson(s, new TypeToken<Map<String, String>>() {
        }.getType());
    }
}
