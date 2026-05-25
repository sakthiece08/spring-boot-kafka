package com.kafka.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class FunctionsConfiguration {

    @Bean
    public Function<String, String> uppercase() {
        return val -> val.toUpperCase();
    }
}
