package vn.insee.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "gift", schema="promotion")
public class GiftEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer type;
    private String name;
    private String data;
    private Integer constructionId;
    private Integer predictId;
    private Integer pointId;
    private Integer customerId;
    private int status;
    private Integer loyaltyId;
    private Integer value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(Integer constructionId) {
        this.constructionId = constructionId;
    }

    public Integer getPredictId() {
        return predictId;
    }

    public void setPredictId(Integer predictId) {
        this.predictId = predictId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public Integer getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(Integer loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
