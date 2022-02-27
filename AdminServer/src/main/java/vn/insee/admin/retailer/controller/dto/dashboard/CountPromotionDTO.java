package vn.insee.admin.retailer.controller.dto.dashboard;

public class CountPromotionDTO {
    private int promotion;
    private int engagement;

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getEngagement() {
        return engagement;
    }

    public void setEngagement(int engagement) {
        this.engagement = engagement;
    }
}
