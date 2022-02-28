package vn.insee.retailer.controller.form;


import java.util.List;

public class StockForm {
    private Integer id;
    private Integer promotionId;
    private String userId;
    private Integer status;
    private Integer type;
    private String detail;
    private List<Integer> cements;
    private Integer bags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<Integer> getCements() {
        return cements;
    }

    public void setCements(List<Integer> cements) {
        this.cements = cements;
    }

    public Integer getBags() {
        return bags;
    }

    public void setBags(Integer bags) {
        this.bags = bags;
    }
}
