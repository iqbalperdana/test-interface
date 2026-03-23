package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.service.WebhookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/webhook")
public class WebhookController {

    @Autowired
    WebhookService webhookService;

    @PostMapping(value = "/{crmName}")
    public ResponseEntity handleWebhook(@PathVariable String crmName, HttpServletRequest platformWebhookRequest) {
        webhookService.handleWebhook(crmName, platformWebhookRequest);
        return ResponseEntity.ok().build();
    }
}