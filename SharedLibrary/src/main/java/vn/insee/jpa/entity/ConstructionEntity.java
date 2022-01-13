package vn.insee.jpa.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "construction", schema="promotion")
@TypeDef(name = "list-array",typeClass = ListArrayType.class)
public class ConstructionEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int city;
    private String district;
    private String name;
    private String phone;
    private int quantity;
    private String address;

    @Type(type = "list-array")
    @Column(name = "bill_ids",columnDefinition = "integer[]")
    private List<Integer> billIds;

    @Type(type = "list-array")
    @Column(name = "image_ids",columnDefinition = "integer[]")
    private List<Integer> imageIds;

    private int estimateTimeStart;
    private int typeConstruction;
    private int type;
    private int status;
    private int promotionId;
    private int customerId;
    private Integer loyaltyId;
    private String extra;
    private Integer labelId;
    private Integer giftId;
    private String note;
    private Integer cement;
    private Integer valueBill;
    private Integer square;
    private Integer floors;
    private Boolean isMixCement;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Integer> getBillIds() {
        return billIds;
    }

    public void setBillIds(List<Integer> billIds) {
        this.billIds = billIds;
    }

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }

    public int getEstimateTimeStart() {
        return estimateTimeStart;
    }

    public void setEstimateTimeStart(int estimateTimeStart) {
        this.estimateTimeStart = estimateTimeStart;
    }

    public int getTypeConstruction() {
        return typeConstruction;
    }

    public void setTypeConstruction(int typeConstruction) {
        this.typeConstruction = typeConstruction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCement() {
        return cement;
    }

    public void setCement(Integer cement) {
        this.cement = cement;
    }

    public Integer getValueBill() {
        return valueBill;
    }

    public void setValueBill(Integer valueBill) {
        this.valueBill = valueBill;
    }

    public Integer getSquare() {
        return square;
    }

    public void setSquare(Integer square) {
        this.square = square;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Boolean getMixCement() {
        return isMixCement;
    }

    public void setMixCement(Boolean mixCement) {
        isMixCement = mixCement;
    }

    public Integer getLoyaltyId() {
        return loyaltyId;
    }

    public void setLoyaltyId(Integer loyaltyId) {
        this.loyaltyId = loyaltyId;
    }
}
