package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.MatchFootballEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchFootballConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public MatchFootballDTO convert2DTO(MatchFootballEntity entity) {
        return mapper.map(entity, MatchFootballDTO.class);
    }

    public PageDTO<MatchFootballDTO> convertToPageDTO(Page<MatchFootballEntity> entities)  {
        List<MatchFootballEntity> content = entities.getContent();
        List<MatchFootballDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (MatchFootballEntity entity: content) {
                dtos.add(convert2DTO(entity));
            }
        }
        return new PageDTO<MatchFootballDTO>(entities.getNumber(), dtos.size(), entities.getTotalPages(), dtos);
    }
}
