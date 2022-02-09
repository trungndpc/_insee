package vn.insee.retailer.bot;

import org.json.JSONObject;

import java.util.Map;

public class LightingSession {
    private long timeStart;
    private int promotionId;
    private String topicId;
    private Map<String, LQQuestionSS> question;
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

    public Map<String, LQQuestionSS> getQuestion() {
        return question;
    }

    public void setQuestion(Map<String, LQQuestionSS> question) {
        this.question = question;
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
        private Class zclass;
        private JSONObject json;

        public Class getZclass() {
            return zclass;
        }

        public void setZclass(Class zclass) {
            this.zclass = zclass;
        }

        public JSONObject getJson() {
            return json;
        }

        public void setJson(JSONObject json) {
            this.json = json;
        }
    }
}
