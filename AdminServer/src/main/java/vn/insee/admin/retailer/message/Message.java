package vn.insee.retailer.bot;

import java.io.Serializable;

public abstract class Message implements Serializable, Conversation {
    private User user;

    public Message(User user) {
        this.user = user;
    }

    public abstract boolean send();

    public User getUser() {
        return user;
    }
}
