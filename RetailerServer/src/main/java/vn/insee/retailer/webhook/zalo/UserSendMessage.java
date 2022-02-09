package vn.insee.retailer.webhook.zalo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class UserSendMessage extends ZaloWebhookMessage {

    public Message message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Message {
        public String text;

        @JsonProperty("msg_id")
        public String msgId;

        @JsonProperty("attachments")
        public List<Attachment> attachments;

    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Attachment {
        public String type;
        public Payload payload;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Payload {
            public String thumbnail;
            public String url;
            public String description;
            public String id;
            public String type;
            public String checksum;

            public static class  Coordinates {
                public String latitude;
                public String longitude;
            }
        }

    }

}


