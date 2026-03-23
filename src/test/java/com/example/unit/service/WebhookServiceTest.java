package com.example.unit.service;

import com.example.service.WebhookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class WebhookServiceTest {

    @Autowired
    private WebhookService webhookService;

    @Test
    public void handleWebhook_whenPlatformNameFound_shouldLogCrmFound1(CapturedOutput output) {
        webhookService.handleWebhook("salesforce", null);
        assertTrue(output.getOut().contains("salesforce"));
    }

    @Test
    public void handleWebhook_whenPlatformNameFound_shouldLogCrmFound2(CapturedOutput output) {
        webhookService.handleWebhook("zendesk", null);
        assertTrue(output.getOut().contains("zendesk"));
    }

    @Test
    public void handleWebhook_whenPlatformNameNotFound_shouldLogNotFound(CapturedOutput output) {
        webhookService.handleWebhook("keap", null);
        assertFalse(output.getOut().contains("Webhook parse for keap called"));
        assertTrue(output.getOut().contains("Failed to handle webhook from keap"));
    }
}
