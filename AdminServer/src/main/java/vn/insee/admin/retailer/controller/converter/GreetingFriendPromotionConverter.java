package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.form.PromotionForm;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;


@Component
public class GreetingFriendPromotionConverter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public GreetingFriendPromotionEntity convert2Entity(PromotionForm form) {
        return mapper.map(form, GreetingFriendPromotionEntity.class);
    }
}
