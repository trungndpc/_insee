package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.admin.retailer.service.PredictFootballFormService;
import vn.insee.common.status.MatchFootballStatus;
import vn.insee.common.status.PredictMatchFootballStatus;
import vn.insee.jpa.entity.MatchFootballEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchFootballConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    @Autowired
    private PredictFootballFormService predictFootballFormService;

    public MatchFootballDTO convert2DTO(MatchFootballEntity entity) {
        MatchFootballDTO dto = mapper.map(entity, MatchFootballDTO.class);
        dto.setTotalPredict(predictFootballFormService.countByMatchAndStatus(entity.getId(), null));
        if (entity.getStatus() == MatchFootballStatus.DONE) {
            long win = predictFootballFormService.countByMatchAndStatus(entity.getId(), PredictMatchFootballStatus.CORRECT_TEAM);
            dto.setTotalWin(win);
            long wrong = predictFootballFormService.countByMatchAndStatus(entity.getId(), PredictMatchFootballStatus.WRONG);
            dto.setTotalFailed(wrong);
        }
        return dto;
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
