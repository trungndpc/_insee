package vn.insee.retailer.bot;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LightingSession {
    private long timeStart;
    private int promotionId;
    private String topicId;
    private Map<String, LQQuestionSS> questions;
    private String waitingQuestionId;

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void putQuestion(String id, LQQuestionSS question) {
        if (questions == null) {
            questions = new HashMap<>();
        }
        questions.put(id, question);
    }

    public Map<String, LQQuestionSS> getQuestions() {
        return questions;
    }

    public String getWaitingQuestionId() {
        return waitingQuestionId;
    }

    public void setWaitingQuestionId(String waitingQuestionId) {
        this.waitingQuestionId = waitingQuestionId;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public static class LQQuestionSS {
        private final String id;
        private final Class zclass;
        private final JSONObject json;

        public LQQuestionSS(String id, Class zclass, JSONObject json) {
            this.id = id;
            this.zclass = zclass;
            this.json = json;
        }

        public String getId() {
            return id;
        }

        public Class getZclass() {
            return zclass;
        }

        public JSONObject getJson() {
            return json;
        }
    }
}
