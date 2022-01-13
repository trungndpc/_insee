package vn.insee.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "history", schema="promotion")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer typePromotionId;
    private Integer promotionId;
    private Integer timeReceived;
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTypePromotionId() {
        return typePromotionId;
    }

    public void setTypePromotionId(Integer typePromotionId) {
        this.typePromotionId = typePromotionId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(Integer timeReceived) {
        this.timeReceived = timeReceived;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
