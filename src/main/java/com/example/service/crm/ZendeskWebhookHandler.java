package com.example.service.crm;

import javax.servlet.http.HttpServletRequest;

import com.example.entity.PlatformName;

import org.springframework.stereotype.Component;

@Component
public class ZendeskWebhookHandler implements WebhookHandler {

    @Override
    public void handle(HttpServletRequest platformWebhookRequest) {
        System.out.printf("Webhook parse for %s called", getPlatformName().getValue());
    }

    @Override
    public PlatformName getPlatformName() {
        return PlatformName.ZENDESK;
    }
}
