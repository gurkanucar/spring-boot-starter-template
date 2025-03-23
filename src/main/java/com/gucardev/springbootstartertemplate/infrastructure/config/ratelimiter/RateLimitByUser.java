package com.gucardev.springbootstartertemplate.infrastructure.config.ratelimiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//@RateLimitByUser("Fizz")
//public String execute(Request request) {

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimitByUser {

    String value() default "default";
}


