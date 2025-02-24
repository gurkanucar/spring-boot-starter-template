package com.gucardev.springbootstartertemplate.infrastructure.config.message;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

  private static MessageSource messageSource;

  public MessageUtil(MessageSource messageSource) {
    MessageUtil.messageSource = messageSource;
  }

  public static String getMessage(String key, Object[] args) {
    try {
      return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    } catch (Exception e) {
      return messageSource.getMessage("messages.default", null, LocaleContextHolder.getLocale());
    }
  }

  public static String getMessage(String key) {
    return getMessage(key, null);
  }

}

