package com.example.service.crm;

import javax.servlet.http.HttpServletRequest;

import com.example.entity.PlatformName;

public interface WebhookHandler {

    public void handle(HttpServletRequest platformWebhookRequest);

    public PlatformName getPlatformName();
}
