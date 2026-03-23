# Interface example #

## Context ##

This repository is serve as an example of interface and implementation where the implementation can be switch during the runtime.
In this case, the project has webhook handler where it may receive from my CRM. If the CRM found in the implementation it will seek the correct
implementation as each CRM webhook parse might be different.

## Implementations ##

Define each platform name on implemented class. Each webhook implementation can have its handler or custom one.

```java
@Component
public class SalesforceWebhookHandler implements WebhookHandler {

    @Override
    public void handle(HttpServletRequest platformWebhookRequest) {
        System.out.printf("Webhook parse for %s called", getPlatformName().getValue());
    }

    @Override
    public PlatformName getPlatformName() {
        return PlatformName.SALESFORCE;
    }
}
```

Register beans on configurations:

```java
@Bean
public Map<PlatformName, WebhookHandler> webhookHandlers(List<WebhookHandler> webhookHandlers) {
    return webhookHandlers.stream().collect(Collectors.toMap(WebhookHandler::getPlatformName, Function.identity()));
}
```

Autowired the map and Spring boot will find the above config bean that match the type:

```java
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
```

Gracefully handle the exception since webhook interaction is fire and forget.

## Endpoints ##

```
POST /api/v1/webhook/{crmName}
```

This will look up with the implemented CRM name and handle accordingly.