package vn.insee.jpa.entity.form;

import vn.insee.jpa.entity.FormEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lighting_quiz_form", schema = "public")
public class LightingQuizFormEntity extends FormEntity {

    private String jsonDetail;
    private int point;

    public String getJsonDetail() {
        return jsonDetail;
    }

    public void setJsonDetail(String jsonDetail) {
        this.jsonDetail = jsonDetail;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
