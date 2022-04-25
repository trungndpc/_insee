package vn.insee.jpa.entity.promotion;

import vn.insee.jpa.entity.PromotionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "predict_football_promotion", schema = "public")
public class PredictFootballPromotionEntity extends PromotionEntity {
    private String season;

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
