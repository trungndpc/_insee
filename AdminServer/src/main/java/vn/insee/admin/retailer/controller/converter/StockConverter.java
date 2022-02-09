package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.StockPromotionDTO;
import vn.insee.admin.retailer.controller.form.PromotionForm;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.promotion.StockPromotionEntity;

@Component
public class StockConverter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public StockPromotionEntity convert2Entity(PromotionForm promotionForm) {
        return mapper.map(promotionForm, StockPromotionEntity.class);
    }

    public StockPromotionDTO convert2DTO(StockPromotionEntity stockPromotionEntity) {
        return mapper.map(stockPromotionEntity, StockPromotionDTO.class);
    }

}
