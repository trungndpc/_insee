package vn.insee.retailer.webhook.zalo;

public class FollowZaloWebhookMessage extends ZaloWebhookMessage{
    public String source;
    public Follower follower;

    public static class Follower {
        public String id;
    }
}
