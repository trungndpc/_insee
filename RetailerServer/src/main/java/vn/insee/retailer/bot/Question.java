package vn.insee.retailer.bot;

import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import java.io.Serializable;
import java.util.UUID;

public abstract class Question implements Serializable, Conversation {
    private User user;
    private String id;

    public Question() {
    }

    public Question(User user, String id) {
        this.user = user;
        this.id = id;
    }

    public Question(User user) {
        this.user = user;
        this.id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
