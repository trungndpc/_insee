package vn.insee.retailer.wrapper.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZaloMessage {
    public String text;
    public Attachment attachment;

    public static ZaloMessage toTextMessage(String text) {
        ZaloMessage message = new ZaloMessage();
        message.text = text;
        return message;
    }

    public static ZaloMessage toImageTemplate(String text, String url) {

        Attachment.Payload.Element element = new Attachment.Payload.Element();
        element.mediaType = "image";
        element.url = url;

        Attachment.Payload payload = new Attachment.Payload();
        payload.templateType = "media";
        payload.elements = Arrays.asList(element);

        Attachment attachment = new Attachment();
        attachment.type = "template";
        attachment.payload = payload;

        ZaloMessage message = new ZaloMessage();
        message.text = text;
        message.attachment = attachment;

        return message;
    }

    public static ZaloMessage toListTemplate(List<Attachment.Payload.Element> elements) {
        Attachment.Payload payload = new Attachment.Payload();
        payload.templateType = "list";
        payload.elements = elements;

        Attachment attachment = new Attachment();
        attachment.type = "template";
        attachment.payload = payload;

        ZaloMessage message = new ZaloMessage();
        message.attachment = attachment;
        return message;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Attachment {
        public String type;
        public Payload payload;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Payload {
            @JsonProperty("template_type")
            public String templateType;
            public List<Element> elements;
            public List<Button> buttons;

            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class Element {
                @JsonProperty("media_type")
                public String mediaType;

                @JsonProperty("attachment_id")
                public String attachmentId;

                public Integer width;
                public Integer height;
                public String title;
                public String subtitle;
                @JsonProperty("image_url")
                public String imageUrl;

                @JsonProperty("url")
                public String url;

                @JsonProperty("default_action")
                public Action action;

                @JsonInclude(JsonInclude.Include.NON_NULL)
                public static class Action {
                    public String type;
                    public String url;
                    public String payload;
                }

            }
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class Button {
                public String title;
                public String type;
                public Object payload;

                @JsonInclude(JsonInclude.Include.NON_NULL)
                public static class ButtonPayload {
                    public String url;
                    public String content;
                    @JsonProperty("phone_code")
                    public String phoneCode;
                }
            }
        }
    }

}