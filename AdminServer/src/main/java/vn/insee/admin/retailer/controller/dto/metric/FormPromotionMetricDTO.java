package vn.insee.admin.retailer.controller.dto.metric;

import vn.insee.admin.retailer.controller.dto.PromotionDTO;

public class FormPromotionMetricDTO {
    private int promotionId;
    private int total;
    private PromotionDTO promotion;

    public FormPromotionMetricDTO() {
    }

    public FormPromotionMetricDTO(int promotionId, int total) {
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

    public PromotionDTO getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionDTO promotion) {
        this.promotion = promotion;
    }
}
