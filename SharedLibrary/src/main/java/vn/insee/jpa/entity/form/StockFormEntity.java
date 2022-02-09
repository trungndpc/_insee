package vn.insee.jpa.entity.form;


import vn.insee.jpa.entity.FormEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stock_form", schema = "public")
public class StockFormEntity extends FormEntity {
    private String jsonImage;
    public String getJsonImage() {
        return jsonImage;
    }
    public void setJsonImage(String jsonImage) {
        this.jsonImage = jsonImage;
    }

}
