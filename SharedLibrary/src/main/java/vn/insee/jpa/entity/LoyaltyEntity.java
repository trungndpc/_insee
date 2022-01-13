package vn.insee.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "loyalty", schema="promotion")
public class LoyaltyEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int customerId;
    private int promotionId;
    private int bags;
    private int ton;
    private Integer giftIdForStarted;
    private Integer firstConstructionId;
    private Integer tonWallProCement;
    private Integer tonReceivedWallProCement;
    private Integer tonPowerSCement;
    private Integer tonReceivedPowerSCement;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public int getTon() {
        return ton;
    }

    public void setTon(int ton) {
        this.ton = ton;
    }

    public Integer getGiftIdForStarted() {
        return giftIdForStarted;
    }

    public void setGiftIdForStarted(Integer giftIdForStarted) {
        this.giftIdForStarted = giftIdForStarted;
    }

    public Integer getFirstConstructionId() {
        return firstConstructionId;
    }

    public void setFirstConstructionId(Integer firstConstructionId) {
        this.firstConstructionId = firstConstructionId;
    }

    public Integer getTonWallProCement() {
        return tonWallProCement;
    }

    public void setTonWallProCement(Integer tonWallProCement) {
        this.tonWallProCement = tonWallProCement;
    }

    public Integer getTonReceivedWallProCement() {
        return tonReceivedWallProCement;
    }

    public void setTonReceivedWallProCement(Integer tonReceivedWallProCement) {
        this.tonReceivedWallProCement = tonReceivedWallProCement;
    }

    public Integer getTonPowerSCement() {
        return tonPowerSCement;
    }

    public void setTonPowerSCement(Integer tonPowerSCement) {
        this.tonPowerSCement = tonPowerSCement;
    }

    public Integer getTonReceivedPowerSCement() {
        return tonReceivedPowerSCement;
    }

    public void setTonReceivedPowerSCement(Integer tonReceivedPowerSCement) {
        this.tonReceivedPowerSCement = tonReceivedPowerSCement;
    }
}
