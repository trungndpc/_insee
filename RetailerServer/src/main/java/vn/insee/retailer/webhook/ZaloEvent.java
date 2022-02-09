package vn.insee.retailer.webhook;

import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;

public abstract class ZaloEvent {
    public abstract boolean process(ZaloWebhookMessage zaloWebhookMessage);
}
