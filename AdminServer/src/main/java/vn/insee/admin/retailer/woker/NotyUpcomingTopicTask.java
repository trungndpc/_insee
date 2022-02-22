package vn.insee.admin.retailer.woker;

public class NotyUpcomingTopicTask {
    private int promotionId;
    private String topicId;

    public NotyUpcomingTopicTask() {
    }

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
}
