package vn.insee.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.GiftEntity;
import vn.insee.retailer.controller.dto.GiftDTO;
import vn.insee.retailer.mapper.Mapper;


@Component
public class GiftConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public GiftDTO convert2DTO(GiftEntity giftEntity) {
        return mapper.map(giftEntity, GiftDTO.class);
    }


}
