package vn.insee.retailer.webhook.zalo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class ZaloWebhookMessage {
    @JsonProperty("app_id")
    public long appId;

    @JsonProperty("oa_id")
    public long oaId;

    @JsonProperty("user_id_by_app")
    public long userIdByApp;

    @JsonProperty("event_name")
    public String eventName;

    @JsonProperty("timestamp")
    public long timestamp;

    @JsonProperty("sender")
    public Sender sender;

    @JsonProperty("recipient")
    public Recipient recipient;

    public static class Sender {
        public String id;
    }

    public static class Recipient {
        public String id;
    }
}
