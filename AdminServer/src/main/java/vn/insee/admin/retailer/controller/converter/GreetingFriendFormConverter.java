package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.GreetingFriendFormDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class GreetingFriendFormConverter {

    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    public GreetingFriendFormDTO convert2DTO(GreetingFriendFormEntity entity) {
        GreetingFriendFormDTO dto =  mapper.map(entity, GreetingFriendFormDTO.class);
        dto.setJsonImgs(entity.getJsonImage());
        Integer userId = entity.getUserId();
        UserEntity userEntity = userService.findById(userId);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setAddress(userEntity.getAddress());
        userDTO.setInseeId(userEntity.getInseeId());
        userDTO.setCityId(userEntity.getCityId());
        userDTO.setDistrictId(userEntity.getDistrictId());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setAvatar(userEntity.getAvatar());
        dto.setUser(userDTO);
        return dto;
    }

    public PageDTO<GreetingFriendFormDTO> convertToPageDTO(Page<GreetingFriendFormEntity> formEntities)  {
        List<GreetingFriendFormEntity> content = formEntities.getContent();
        List<GreetingFriendFormDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (GreetingFriendFormEntity entity: content) {
                dtos.add(convert2DTO(entity));
            }
        }
        return new PageDTO<>(formEntities.getNumber(), dtos.size(), formEntities.getTotalPages(), dtos);
    }


    public List<GreetingFriendFormDTO> convertToListDTO(List<GreetingFriendFormEntity> formEntities)  {
        List<GreetingFriendFormDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(formEntities)) {
            for (GreetingFriendFormEntity entity: formEntities) {
                dtos.add(convert2DTO(entity));
            }
        }
        return dtos;
    }

}
