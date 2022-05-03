package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.controller.dto.PredictMatchFootballDTO;
import vn.insee.admin.retailer.controller.dto.UserDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.admin.retailer.service.MatchFootballService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class PredictMatchFootballFormConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchFootballService matchFootballService;

    @Autowired
    private Mapper mapper;

    public PredictMatchFootballDTO convert2DTO(PredictMatchFootballFormEntity entity) {
        PredictMatchFootballDTO predictMatchFootballDTO = mapper.map(entity, PredictMatchFootballDTO.class);
        UserEntity userEntity = userService.findById(entity.getUserId());
        predictMatchFootballDTO.setUser(mapper.map(userEntity, UserDTO.class));

        MatchFootballEntity matchFootballEntity = matchFootballService.getById(entity.getMatchId());
        predictMatchFootballDTO.setMatch(mapper.map(matchFootballEntity, MatchFootballDTO.class));
        return predictMatchFootballDTO;
    }

    public PageDTO<PredictMatchFootballDTO> convertToPageDTO(Page<PredictMatchFootballFormEntity> entities)  {
        List<PredictMatchFootballFormEntity> content = entities.getContent();
        List<PredictMatchFootballDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (PredictMatchFootballFormEntity entity: content) {
                dtos.add(convert2DTO(entity));
            }
        }
        return new PageDTO<PredictMatchFootballDTO>(entities.getNumber(), dtos.size(), entities.getTotalPages(), dtos);
    }
}
