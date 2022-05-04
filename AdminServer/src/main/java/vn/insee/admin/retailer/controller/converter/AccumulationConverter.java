package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.AccumulationDTO;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.jpa.entity.AccumulationEntity;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccumulationConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    @Autowired
    private UserService userService;

    public AccumulationDTO convert2DTO(AccumulationEntity entity) {
        AccumulationDTO accumulationDTO = mapper.map(entity, AccumulationDTO.class);
        UserEntity userEntity = userService.findById(entity.getUid());
        accumulationDTO.setUser(mapper.map(userEntity, UserDTO.class));
        return accumulationDTO;
    }

    public PageDTO<AccumulationDTO> convertToPageDTO(Page<AccumulationEntity> entities)  {
        List<AccumulationEntity> content = entities.getContent();
        List<AccumulationDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (AccumulationEntity entity: content) {
                dtos.add(convert2DTO(entity));
            }
        }
        return new PageDTO<AccumulationDTO>(entities.getNumber(), dtos.size(), entities.getTotalPages(), dtos);
    }
}
