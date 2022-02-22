package vn.insee.admin.retailer.controller.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.*;
import vn.insee.admin.retailer.controller.dto.metric.FormCityMetricDTO;
import vn.insee.admin.retailer.controller.dto.metric.FormDateMetricDTO;
import vn.insee.admin.retailer.controller.dto.metric.FormPromotionMetricDTO;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.metric.FormCityMetric;
import vn.insee.jpa.metric.FormDateMetric;
import vn.insee.jpa.metric.FormPromotionMetric;
import vn.insee.jpa.repository.PromotionRepository;
import vn.insee.jpa.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class FormConverter {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    public FormDateMetricDTO map(FormDateMetric metric) {
        return mapper.map(metric, FormDateMetricDTO.class);
    }

    public FormCityMetricDTO map(FormCityMetric metric) {
        return mapper.map(metric, FormCityMetricDTO.class);
    }

    public FormPromotionMetricDTO map(FormPromotionMetric metric) {
        FormPromotionMetricDTO dto = mapper.map(metric, FormPromotionMetricDTO.class);
        PromotionEntity one = promotionRepository.getOne(metric.getPromotionId());
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setType(one.getType());
        promotionDTO.setTitle(one.getTitle());
        promotionDTO.setTimeStart(one.getTimeStart());
        dto.setPromotion(promotionDTO);
        return dto;
    }

    public FormDTO convert2FormDTO(FormEntity formEntity) {
        FormDTO formDTO = mapper.map(formEntity, FormDTO.class);;
        UserEntity userEntity = userRepository.getOne(formDTO.getUserId());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        formDTO.setTime(formEntity.getUpdatedTime().toEpochSecond());
        userDTO.setAvatar(userEntity.getAvatar());
        userDTO.setInseeId(userEntity.getInseeId());
        userDTO.setCityId(userEntity.getCityId());
        formDTO.setUser(userDTO);
        PromotionEntity promotionEntity = promotionRepository.getOne(formEntity.getPromotionId());
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId(promotionEntity.getId());
        promotionDTO.setTitle(promotionEntity.getTitle());
        promotionDTO.setType(promotionEntity.getType());
        formDTO.setPromotion(promotionDTO);
        return formDTO;
    }

    public StockFormDTO convert2StockFormDTO(StockFormEntity stockFormEntity) {
        StockFormDTO formDTO = mapper.map(stockFormEntity, StockFormDTO.class);
        formDTO.setJsonImgs(stockFormEntity.getJsonImage());
        formDTO.setTime(stockFormEntity.getUpdatedTime().toEpochSecond());
        UserEntity userEntity = userRepository.getOne(formDTO.getUserId());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setAvatar(userEntity.getAvatar());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setDistrictId(userEntity.getDistrictId());
        userDTO.setCityId(userEntity.getCityId());
        userDTO.setAddress(userEntity.getAddress());
        formDTO.setUser(userDTO);
        PromotionEntity promotionEntity = promotionRepository.getOne(stockFormEntity.getPromotionId());
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId(promotionEntity.getId());
        promotionDTO.setTitle(promotionEntity.getTitle());
        promotionDTO.setType(promotionEntity.getType());
        formDTO.setPromotion(promotionDTO);
        return formDTO;
    }

    public PageDTO<FormDTO> convertToPageDTO(Page<FormEntity> formEntities)  {
        List<FormEntity> content = formEntities.getContent();
        List<FormDTO> dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(content)) {
            for (FormEntity entity: content) {
                dtos.add(convert2FormDTO(entity));
            }
        }
        return new PageDTO<FormDTO>(formEntities.getNumber(), dtos.size(), formEntities.getTotalPages(), dtos);
    }


}
