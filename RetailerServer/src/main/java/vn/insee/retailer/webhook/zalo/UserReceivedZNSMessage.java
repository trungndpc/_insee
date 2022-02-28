package vn.insee.retailer.webhook.zalo;

public class UserReceivedZNSMessage extends ZaloWebhookMessage{
    private Content message;

    public Content getMessage() {
        return message;
    }

    public void setMessage(Content message) {
        this.message = message;
    }

    public static class Content {
        private String deliveryTime;
        private String msgId;
        private String trackingId;

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }
}
}
