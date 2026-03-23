# Interface example #

## Context ##

This repository is serve as an example of interface and implementation where the implementation can be switch during the runtime.
In this case, the project has webhook handler where it may receive from my CRM. If the CRM found in the implementation it will seek the correct
implementation as each CRM webhook parse might be different.

## Implementations ##

Define interface for webhook handler that will be implemented by each CRM.

```java
public interface WebhookHandler {

    public void handle(HttpServletRequest platformWebhookRequest);

    public PlatformName getPlatformName();
}
```

Define each CRM platform name on implemented class. Each webhook implementation can have its handler or custom one.

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

Register beans on configurations with mapping of platform name and its handler

```java
@Bean
public Map<PlatformName, WebhookHandler> webhookHandlers(List<WebhookHandler> webhookHandlers) {
    return webhookHandlers.stream().collect(Collectors.toMap(WebhookHandler::getPlatformName, Function.identity()));
}
```

Autowired the map and Spring boot will find the above config bean that match the type.
System will read the CRM platfrom name from input and get its handler in the map.

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

## Test ##

Run the unit test to see the implementation

```java
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
```