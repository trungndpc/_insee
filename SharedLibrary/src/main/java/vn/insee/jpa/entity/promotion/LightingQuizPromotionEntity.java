package vn.insee.jpa.entity.promotion;

import vn.insee.jpa.entity.PromotionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lighting_quiz_promotion", schema = "public")
public class LightingQuizPromotionEntity extends PromotionEntity {
    private String topics;

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }
}
