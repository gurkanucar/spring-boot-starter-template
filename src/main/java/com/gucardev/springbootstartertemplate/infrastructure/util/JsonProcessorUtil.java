package com.gucardev.springbootstartertemplate.infrastructure.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class JsonProcessorUtil {

  private final ObjectMapper objectMapper;


//    jsonProcessorUtil.processJson("data/Customers.json", CustomerDto.class, customerService::create);

  public <T> void processJson(String resourcePath, Class<T> type, Consumer<T> consumer)
      throws Exception {
    InputStream is = new ClassPathResource(resourcePath).getInputStream();
    List<T> items =
        objectMapper.readValue(
            is, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
    items.forEach(consumer);
  }
}
