package com.gucardev.springbootstartertemplate.infrastructure.exception.helper;

import com.gucardev.springbootstartertemplate.infrastructure.config.message.MessageUtil;
import com.gucardev.springbootstartertemplate.infrastructure.exception.ExceptionMessage;
import com.gucardev.springbootstartertemplate.infrastructure.exception.model.CustomException;
import org.springframework.stereotype.Component;

@Component
public class ExceptionUtil {

    public ExceptionUtil() {
    }

    public static CustomException buildException() {
        return buildException(ExceptionMessage.DEFAULT_EXCEPTION);
    }

    public static CustomException buildException(String ex) {
        return new CustomException(ex, ExceptionMessage.DEFAULT_EXCEPTION.getStatus());
    }

    public static CustomException buildException(ExceptionMessage ex, Object... args) {
        String errorMessage = MessageUtil.getMessage(ex.getKey(), args);
        return new CustomException(errorMessage, ex.getStatus());
    }

    public static CustomException buildException(ExceptionMessage ex) {
        String errorMessage = MessageUtil.getMessage(ex.getKey());
        return new CustomException(errorMessage, ex.getStatus());
    }

    public static CustomException buildSilentException(ExceptionMessage ex) {
        String errorMessage = MessageUtil.getMessage(ex.getKey());
        return new CustomException(errorMessage, ex.getStatus(), false);
    }

    public static CustomException buildSilentException(ExceptionMessage ex, Object... args) {
        String errorMessage = MessageUtil.getMessage(ex.getKey(), args);
        return new CustomException(errorMessage, ex.getStatus(), false);
    }
}