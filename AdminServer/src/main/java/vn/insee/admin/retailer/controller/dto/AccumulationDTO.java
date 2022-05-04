package vn.insee.admin.retailer.controller.dto;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import java.util.List;

public class AccumulationDTO {
    private Integer id;
    private int uid;
    private List<Integer> promotionIds;
    private Integer point;
    private int type;
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<Integer> getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(List<Integer> promotionIds) {
        this.promotionIds = promotionIds;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
