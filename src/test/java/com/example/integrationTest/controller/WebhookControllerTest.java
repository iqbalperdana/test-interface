package com.example.integrationTest.controller;

import com.example.integrationTest.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({OutputCaptureExtension.class})
public class WebhookControllerTest extends BaseTest {

    @Test
    public void handleWebhook_shouldReturnOk(CapturedOutput output) throws Exception {
        mockMvc.perform(post("/api/v1/webhook/zendesk"))
            .andExpect(status().isOk());

        assertTrue(output.getOut().contains("zendesk"));
    }

    @Test
    public void handleWebhook_shouldReturnOkAndLogWebhookParse(CapturedOutput output) throws Exception {
        mockMvc.perform(post("/api/v1/webhook/salesforce"))
            .andExpect(status().isOk());

        assertTrue(output.getOut().contains("Webhook parse for salesforce called"));
        assertFalse(output.getOut().contains("Failed to handle webhook from salesforce"));
    }

    @Test
    public void handleWebhook_withCRMNotSupported_shouldReturnOkAndFailed(CapturedOutput output) throws Exception {
        mockMvc.perform(post("/api/v1/webhook/keap"))
            .andExpect(status().isOk());

        assertFalse(output.getOut().contains("zendesk"));
        assertFalse(output.getOut().contains("Webhook parse for keap called"));
        assertTrue(output.getOut().contains("Failed to handle webhook from keap"));
    }

}
