package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.GiftDTO;
import vn.insee.admin.retailer.controller.form.GiftForm;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.common.type.TypeGift;
import vn.insee.jpa.entity.GiftEntity;

@Component
public class GiftConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public GiftEntity convertFromForm(GiftForm form) throws JsonProcessingException {
        GiftEntity giftEntity = mapper.map(form, GiftEntity.class);
        if (form.getType() == TypeGift.CARD_PHONE) {
            giftEntity.setContent(objectMapper.writeValueAsString(form.getCardPhones()));
        }
        return giftEntity;
    }

    public GiftDTO convert2DTO(GiftEntity entity) {
        return mapper.map(entity, GiftDTO.class);
    }
}
