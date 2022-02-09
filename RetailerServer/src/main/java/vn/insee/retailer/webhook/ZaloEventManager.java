package vn.insee.retailer.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.retailer.bot.LightingSession;
import vn.insee.retailer.service.UserService;

import java.util.*;

@Service
public class ZaloEventManager {
    private static final Logger LOGGER = LogManager.getLogger(ZaloEventManager.class);
    public static final ZaloEventManager INSTANCE = new ZaloEventManager();
    private static List<ZaloEvent> EVENTS = Arrays.asList(new FollowZaloEvent());
    private static Map<Integer, Object> SESSIONS = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FollowZaloEvent followZaloEvent;

    @Autowired
    private UserService userService;

    public void doing(JSONObject zaloMsg) throws Exception {
        LOGGER.info(zaloMsg);
        if (followZaloEvent.isAccepted(zaloMsg)) {
            followZaloEvent.process(zaloMsg);
            return;
        }
        long userByAppId = zaloMsg.getLong("user_id_by_app");
        LOGGER.info("userByAppId: " + userByAppId);
        UserEntity userEntity = userService.findByZaloId(userByAppId);
        if (userEntity == null) {
            throw new Exception("not found user user_id_by_app: " + userByAppId + ", zaloMsg: " + zaloMsg);
        }
        Object session = SESSIONS.getOrDefault(userEntity.getId(), null);
        LOGGER.info("session: " + session);
        if (session instanceof LightingSession) {
            LightingSession lqSession = (LightingSession) session;
        }
    }

    public void put(int uid, Object session) {
        SESSIONS.put(uid, session);
    }



}
