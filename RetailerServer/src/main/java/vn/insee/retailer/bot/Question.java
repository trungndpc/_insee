package vn.insee.retailer.bot;

import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import java.io.Serializable;

public abstract class Question implements Serializable, Conversation {
    private User user;

    public Question() {
    }

    public Question(User user) {
        this.user = user;
    }

    public abstract boolean ask();
    public abstract boolean ans(ZaloWebhookMessage msg);
    public abstract Object getUserAnswer();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
