package vn.insee.retailer.controller.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.repository.PromotionRepository;
import vn.insee.retailer.controller.dto.FormHistoryDTO;
import vn.insee.retailer.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormConverter {
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
        return historyDTO;
    }

    public List<FormHistoryDTO> convert2ListFormHistoryDTO(List<FormEntity> formEntityList) {
        return formEntityList.stream().map(this::convert2FormHistoryDTO)
                .collect(Collectors.toList());
    }
}
