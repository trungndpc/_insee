package vn.insee.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.juli.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.repository.PromotionRepository;
import vn.insee.retailer.controller.dto.FormHistoryDTO;
import vn.insee.retailer.mapper.Mapper;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormConverter {
    private static final Logger lOGGER = LogManager.getLogger();
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private Mapper mapper;

    public FormHistoryDTO convert2FormHistoryDTO(FormEntity formEntity) {
        FormHistoryDTO historyDTO = mapper.map(formEntity, FormHistoryDTO.class);
        PromotionEntity promotionEntity = promotionRepository.getOne(historyDTO.getPromotionId());
        historyDTO.setPromotionName(promotionEntity.getTitle());
        historyDTO.setTime(formEntity.getUpdatedTime().toEpochSecond());
        if (formEntity.getGifts() != null) {
            historyDTO.setGiftId(formEntity.getGifts().get(0));
        }
        return historyDTO;
    }

    public List<FormHistoryDTO> convert2ListFormHistoryDTO(List<FormEntity> formEntityList) {
        return formEntityList.stream().map(this::convert2FormHistoryDTO)
                .collect(Collectors.toList());
    }
}
