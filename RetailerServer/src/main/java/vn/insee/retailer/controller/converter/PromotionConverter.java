package vn.insee.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.retailer.controller.dto.PromotionDTO;
import vn.insee.retailer.mapper.Mapper;


@Component
public class PromotionConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public PromotionDTO convert2DTO(PromotionEntity promotionEntity) {
        return mapper.map(promotionEntity, PromotionDTO.class);
    }


}
