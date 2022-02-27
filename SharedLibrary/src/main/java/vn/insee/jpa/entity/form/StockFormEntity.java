package vn.insee.jpa.entity.form;


import vn.insee.jpa.entity.FormEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stock_form", schema = "public")
public class StockFormEntity extends FormEntity {
    private String jsonImage;
    private Integer bags;
//    private Integer[] cements;

    public String getJsonImage() {
        return jsonImage;
    }

    public void setJsonImage(String jsonImage) {
        this.jsonImage = jsonImage;
    }

    public Integer getBags() {
        return bags;
    }

    public void setBags(Integer bags) {
        this.bags = bags;
    }

//    public Integer[] getCements() {
//        return cements;
//    }
//
//    public void setCements(Integer[] cements) {
//        this.cements = cements;
//    }
}
