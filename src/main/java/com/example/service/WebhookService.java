package com.example.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.entity.PlatformName;
import com.example.service.crm.WebhookHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    @Autowired
    private Map<PlatformName, WebhookHandler> webhookHandlers;

    public void handleWebhook(String crmName, HttpServletRequest webhookRequest) {
        try {
            PlatformName platformName = PlatformName.fromValue(crmName);
            WebhookHandler webhookHandler = webhookHandlers.get(platformName);
            webhookHandler.handle(webhookRequest);
        } catch (Exception e) {
            System.out.println("Failed to handle webhook from " + crmName);
        }
    }
}
