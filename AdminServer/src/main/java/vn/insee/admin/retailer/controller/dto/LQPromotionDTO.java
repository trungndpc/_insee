package vn.insee.admin.retailer.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LQPromotionDTO extends PromotionDTO{
    @JsonRawValue
    private String topics;

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }
}
