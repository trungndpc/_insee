package vn.insee.jpa.metric;

public class FormPromotionMetric {
    private int promotionId;
    private int total;

    public FormPromotionMetric() {
    }

    public FormPromotionMetric(int promotionId, int total) {
        this.promotionId = promotionId;
        this.total = total;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
