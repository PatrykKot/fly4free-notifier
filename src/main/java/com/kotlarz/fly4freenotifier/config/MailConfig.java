package com.kotlarz.fly4freenotifier.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kotlarz.fly4freenotifier.service.util.MailCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class MailConfig {
    private static final String CREDENTIALS_PATH = "credentials.json";

    private static MailCredentials readCredentials() throws IOException {
        File file = new File(CREDENTIALS_PATH);
        if (!file.exists()) {
            throw new IllegalArgumentException("Email credentials.json does not exist");
        }

        return new ObjectMapper().readValue(file, MailCredentials.class);
    }

    @Bean
    public JavaMailSender getJavaMailSender() throws IOException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        MailCredentials credentials = readCredentials();
        mailSender.setUsername(credentials.getUser() + "@gmail.com");
        mailSender.setPassword(credentials.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
