package vn.insee.admin.retailer.controller.form;

import com.fasterxml.jackson.annotation.JsonRawValue;
import vn.insee.admin.retailer.controller.dto.gift.PhoneCardGift;

import java.util.List;

public class GiftForm {
    private Integer id;
    private String content;
    private Integer status;
    private Integer userId;
    private String title;
    private Integer type;
    private List<PhoneCardGift> cardPhones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<PhoneCardGift> getCardPhones() {
        return cardPhones;
    }

    public void setCardPhones(List<PhoneCardGift> cardPhones) {
        this.cardPhones = cardPhones;
    }
}
