package vn.insee.retailer.webhook.zalo;

public class UserReceivedZNSMessage extends ZaloWebhookMessage {
    private Content message;

    public Content getMessage() {
        return message;
    }

    public void setMessage(Content message) {
        this.message = message;
    }

    public static class Content {
        private String delivery_time;
        private String msg_id;
        private String tracking_id;

        public String getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(String delivery_time) {
            this.delivery_time = delivery_time;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getTracking_id() {
            return tracking_id;
        }

        public void setTracking_id(String tracking_id) {
            this.tracking_id = tracking_id;
        }
    }
}
