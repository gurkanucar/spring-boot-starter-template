package com.gucardev.springbootstartertemplate.infrastructure.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gucardev.springbootstartertemplate.infrastructure.config.message.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;


//// Using default messages
//SuccessResponse.ok(data);
//
//// Using custom literal message
//SuccessResponse.ok(data, "Operation was successful");
//
//// Using message key
//SuccessResponse.ok(data, "response.success.custom");
//
//// Using the builder with message key
//return ResponseEntity.ok(
//        SuccessResponse.<User>builder()
//        .status(HttpStatus.OK)
//        .messageKey("response.user.created")
//        .data(user)
//        .build()
//);

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "error",
        "status",
        "message",
        "data",
        "time"
})
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<T> {

    private boolean isError = false;
    private HttpStatus status;
    private String message;
    private T data;
    private String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    public static <T> SuccessResponseBuilder<T> builder() {
        return new SuccessResponseBuilder<>();
    }

    public static class SuccessResponseBuilder<T> {
        private HttpStatus status;
        private String message;
        private T data;
        private String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        SuccessResponseBuilder() {
        }

        public SuccessResponseBuilder<T> status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public SuccessResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public SuccessResponseBuilder<T> messageKey(String messageKey) {
            this.message = MessageUtil.getMessage(messageKey);
            return this;
        }

        public SuccessResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public SuccessResponseBuilder<T> time(String time) {
            this.time = time;
            return this;
        }

        public SuccessResponse<T> build() {
            boolean isError = false;
            return new SuccessResponse<>(isError, status, message, data, time);
        }
    }

    public static <T> ResponseEntity<SuccessResponse<T>> ok(T data) {
        return ResponseEntity.ok(
                SuccessResponse.<T>builder()
                        .status(HttpStatus.OK)
                        .messageKey("response.success.default")
                        .data(data)
                        .build()
        );
    }

    public static <T> ResponseEntity<SuccessResponse<T>> ok(T data, String message) {
        return ResponseEntity.ok(
                SuccessResponse.<T>builder()
                        .status(HttpStatus.OK)
                        .message(message.startsWith("response.") ? MessageUtil.getMessage(message) : message)
                        .data(data)
                        .build()
        );
    }

    public static <T> ResponseEntity<SuccessResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.<T>builder()
                        .status(HttpStatus.CREATED)
                        .messageKey("response.success.created")
                        .data(data)
                        .build()
        );
    }

    public static <T> ResponseEntity<SuccessResponse<T>> created(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.<T>builder()
                        .status(HttpStatus.CREATED)
                        .message(message.startsWith("response.") ? MessageUtil.getMessage(message) : message)
                        .data(data)
                        .build()
        );
    }

    public static <T> ResponseEntity<SuccessResponse<T>> buildResponse(HttpStatus status, T data, String message) {
        return ResponseEntity.status(status).body(
                SuccessResponse.<T>builder()
                        .status(status)
                        .message(message.startsWith("response.") ? MessageUtil.getMessage(message) : message)
                        .data(data)
                        .build()
        );
    }

    public static <T> ResponseEntity<SuccessResponse<T>> buildResponse(HttpStatus status, T data) {
        return buildResponse(status, data, MessageUtil.getMessage("response.success.default"));
    }
}