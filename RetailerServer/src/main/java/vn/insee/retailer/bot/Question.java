package vn.insee.retailer.bot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import vn.insee.retailer.webhook.zalo.ZaloWebhookMessage;
import java.io.Serializable;
import java.util.UUID;

public abstract class Question implements Serializable, Conversation {
    protected static final ObjectMapper mapper = new ObjectMapper();
    private User user;
    private String id;


    public Question(JSONObject data) throws JsonProcessingException {
        this.id = data.getString("id");
        this.user = mapper.readValue(data.getJSONObject("user").toString(), User.class);
    }

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

    @JsonIgnore
    public abstract boolean ask();
    @JsonIgnore
    public abstract boolean ans(ZaloWebhookMessage msg);
    @JsonIgnore
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
