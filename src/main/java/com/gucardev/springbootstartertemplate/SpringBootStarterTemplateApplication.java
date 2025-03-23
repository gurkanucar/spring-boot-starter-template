package com.gucardev.springbootstartertemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class SpringBootStarterTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterTemplateApplication.class, args);
    }

}
