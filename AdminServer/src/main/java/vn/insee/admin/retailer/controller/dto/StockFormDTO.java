package vn.insee.admin.retailer.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockFormDTO extends FormDTO {
    @JsonRawValue
    private String jsonImg;

    public String getJsonImg() {
        return jsonImg;
    }

    public void setJsonImg(String jsonImg) {
        this.jsonImg = jsonImg;
    }
}
