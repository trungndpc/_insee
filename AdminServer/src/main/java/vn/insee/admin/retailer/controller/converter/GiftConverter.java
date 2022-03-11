package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.GiftDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.controller.form.GiftForm;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.type.TypeGift;
import vn.insee.jpa.entity.GiftEntity;
import vn.insee.jpa.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class GiftConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

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

    public PageDTO<GiftDTO> convertToPageDTO(Page<GiftEntity> giftEntityPage)  {
        List<GiftEntity> content = giftEntityPage.getContent();
        List<GiftDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (GiftEntity entity: content) {
                GiftDTO giftDTO = convert2DTO(entity);
                UserEntity userEntity = userService.findById(entity.getUserId());
                UserDTO userDTO = new UserDTO();
                userDTO.setPhone(userEntity.getPhone());
                userDTO.setName(userEntity.getName());
                userDTO.setAvatar(userEntity.getAvatar());
                giftDTO.setUser(userDTO);
                dtos.add(giftDTO);
            }
        }
        return new PageDTO<GiftDTO>(giftEntityPage.getNumber(), dtos.size(), giftEntityPage.getTotalPages(), dtos);
    }
}
