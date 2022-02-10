package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.controller.dto.lq.LQFormDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.jpa.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class LQFormConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper mapper;

    public LQFormDTO covert2DTO(LightingQuizFormEntity entity) {
        LQFormDTO dto = mapper.map(entity, LQFormDTO.class);
        UserEntity userEntity = userRepository.getOne(dto.getUserId());
        dto.setUser(mapper.map(userEntity, UserDTO.class));
        return dto;
    }

    public PageDTO<LQFormDTO> convertToPageDTO(Page<LightingQuizFormEntity> formEntities)  {
        List<LightingQuizFormEntity> content = formEntities.getContent();
        List<LQFormDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (LightingQuizFormEntity entity: content) {
                dtos.add(covert2DTO(entity));
            }
        }
        return new PageDTO<>(formEntities.getNumber(), dtos.size(), formEntities.getTotalPages(), dtos);
    }
}
