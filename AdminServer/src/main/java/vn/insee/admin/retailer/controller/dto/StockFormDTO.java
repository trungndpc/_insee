package vn.insee.admin.retailer.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockFormDTO extends FormDTO {
    @JsonRawValue
    private String jsonImgs;

    public String getJsonImgs() {
        return jsonImgs;
    }

    public void setJsonImgs(String jsonImgs) {
        this.jsonImgs = jsonImgs;
    }
}
