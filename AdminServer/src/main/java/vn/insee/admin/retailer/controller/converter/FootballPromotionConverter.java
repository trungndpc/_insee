package vn.insee.admin.retailer.controller.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.form.PromotionForm;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;

@Component
public class FootballPromotionConverter {

    @Autowired
    private Mapper mapper;

    public PredictFootballPromotionEntity convert2Entity(PromotionForm form) {
        return mapper.map(form, PredictFootballPromotionEntity.class);
    }
}
