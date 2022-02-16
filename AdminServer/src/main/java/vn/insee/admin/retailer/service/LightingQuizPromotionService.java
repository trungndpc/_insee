package vn.insee.admin.retailer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.controller.converter.LQConverter;
import vn.insee.admin.retailer.controller.dto.LQPromotionDTO;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.common.status.StatusLightingQuizPromotion;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.repository.LightingQuizPromotionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LightingQuizPromotionService {

    @Autowired
    private LightingQuizPromotionRepository lzQuizPromotionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public LightingQuizPromotionEntity get(int id) {
        return lzQuizPromotionRepository.getOne(id);
    }

    public LightingQuizPromotionEntity create(LightingQuizPromotionEntity lightingQuizPromotionEntity) {
        lightingQuizPromotionEntity.setStatus(StatusPromotion.INIT);
        return lzQuizPromotionRepository.saveAndFlush(lightingQuizPromotionEntity);
    }

    public LightingQuizPromotionEntity update(LightingQuizPromotionEntity promotionEntity) {
        return lzQuizPromotionRepository.saveAndFlush(promotionEntity);
    }

    public boolean updateStatusTopic(int promotionId, String topicId, int status) throws Exception {
        LightingQuizPromotionEntity promotionEntity = lzQuizPromotionRepository.getOne(promotionId);
        String strTopics = promotionEntity.getTopics();
        if (strTopics == null || strTopics.isEmpty()) {
            throw new Exception("topic is empty promotionId: " + promotionId);
        }
        List<TopicDTO> topicDTOS = objectMapper.readValue(strTopics, new TypeReference<List<TopicDTO>>() {
        });
        topicDTOS.stream().forEach(topicDTO -> {
            if (topicDTO.getId().equals(topicId)) {
                topicDTO.setStatus(status);
            }
        });
        promotionEntity.setTopics(objectMapper.writeValueAsString(topicDTOS));
        lzQuizPromotionRepository.saveAndFlush(promotionEntity);
        return true;
    }
}
