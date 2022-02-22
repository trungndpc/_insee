package vn.insee.admin.retailer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.woker.NotyUpcomingTopicTask;
import vn.insee.admin.retailer.woker.Scheduler;
import vn.insee.common.status.StatusPromotion;
import vn.insee.common.status.StatusTopicLightingQuizPromotion;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.repository.LightingQuizPromotionRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LightingQuizPromotionService {

    @Autowired
    private LightingQuizPromotionRepository repository;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ObjectMapper objectMapper;

    public LightingQuizPromotionEntity get(int id) {
        return repository.getOne(id);
    }

    public LightingQuizPromotionEntity create(LightingQuizPromotionEntity lightingQuizPromotionEntity) {
        lightingQuizPromotionEntity.setStatus(StatusPromotion.INIT);
        return repository.saveAndFlush(lightingQuizPromotionEntity);
    }

    public LightingQuizPromotionEntity update(LightingQuizPromotionEntity promotionEntity) {
        return repository.saveAndFlush(promotionEntity);
    }

    public boolean updateStatusTopic(int promotionId, String topicId, int status) throws Exception {
        LightingQuizPromotionEntity promotionEntity = repository.getOne(promotionId);
        String strTopics = promotionEntity.getTopics();
        if (strTopics == null || strTopics.isEmpty()) {
            throw new Exception("topic is empty promotionId: " + promotionId);
        }
        List<TopicDTO> topicDTOS = objectMapper.readValue(strTopics, new TypeReference<List<TopicDTO>>() {
        });
        AtomicLong timeStart = new AtomicLong();
        topicDTOS.stream().forEach(topicDTO -> {
            if (topicDTO.getId().equals(topicId)) {
                topicDTO.setStatus(status);
                timeStart.set(topicDTO.getTimeStart());
            }
        });
        if (status == StatusTopicLightingQuizPromotion.APPROVED) {
            LocalDateTime triggerTime5min =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStart.get() - 5 * 60 * 1000),
                            ZoneId.of("Asia/Ho_Chi_Minh"));
            NotyUpcomingTopicTask task = new NotyUpcomingTopicTask();
            task.setPromotionId(promotionId);
            task.setTopicId(topicId);
            scheduler.addJob(triggerTime5min, task);
            LocalDateTime triggerTimeStart =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStart.get()),
                            ZoneId.of("Asia/Ho_Chi_Minh"));
            scheduler.addJob2StartTopicLQPromotion(triggerTimeStart, task);
        }
        promotionEntity.setTopics(objectMapper.writeValueAsString(topicDTOS));
        repository.saveAndFlush(promotionEntity);
        return true;
    }

    public List<TopicDTO> getListTopicDTO(LightingQuizPromotionEntity entity)
            throws JsonProcessingException {
        String strTopic = entity.getTopics();
        if (strTopic == null || strTopic.isEmpty()) {
            strTopic = "[]";
        }
        return objectMapper.readValue(strTopic, new TypeReference<List<TopicDTO>>() {
        });
    }

}
