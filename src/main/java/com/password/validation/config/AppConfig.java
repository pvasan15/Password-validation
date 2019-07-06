package com.password.validation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.password.validation.client.Client;
import com.password.validation.service.PasswordService;

@Configuration
public class AppConfig {

    public @Bean
    PasswordService passwordService() {
        return new PasswordService();
    }

    public @Bean
    Client client() {
        return new Client();
    }

}