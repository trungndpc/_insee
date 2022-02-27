package vn.insee.admin.retailer.controller.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockFormDTO extends FormDTO {
    @JsonRawValue
    private String jsonImgs;
    private List<Integer> cements;
    private Integer bags;
    private String note;

    public String getJsonImgs() {
        return jsonImgs;
    }

    public void setJsonImgs(String jsonImgs) {
        this.jsonImgs = jsonImgs;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
