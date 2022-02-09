package vn.insee.retailer.webhook;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebhookSessionManager {
    private static Map<Integer, Object> SESSIONS = new HashMap<>();

    public Object getCurrentSession(int uid) {
        return SESSIONS.getOrDefault(uid, null);
    }

    public void saveSession(int uid, Object session) {
        SESSIONS.put(uid, session);
    }

    public void clearSession(int uid, Object session) {
        SESSIONS.remove(uid);
    }

}
