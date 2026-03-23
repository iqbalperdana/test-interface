package com.example.common.configuration;

import com.example.entity.PlatformName;
import com.example.service.crm.WebhookHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class WebhookConfiguration {

    @Bean
    public Map<PlatformName, WebhookHandler> webhookHandlers(List<WebhookHandler> webhookHandlers) {
        return webhookHandlers.stream().collect(Collectors.toMap(WebhookHandler::getPlatformName, Function.identity()));
    }
}
