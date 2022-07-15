package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.GreetingFriendFormDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class GreetingFriendConverter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public GreetingFriendFormDTO convert2DTO(GreetingFriendFormEntity entity) {
        return mapper.map(entity, GreetingFriendFormDTO.class);
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
