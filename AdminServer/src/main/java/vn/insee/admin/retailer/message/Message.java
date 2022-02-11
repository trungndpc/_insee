package vn.insee.admin.retailer.message;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private User user;

    public Message(User user) {
        this.user = user;
    }

    public abstract boolean send();

    public User getUser() {
        return user;
    }
}
