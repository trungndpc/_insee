package vn.insee.retailer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.repository.LightingQuizPromotionRepository;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.message.Before5MinMessage;
import vn.insee.retailer.bot.script.LightingQuizScript;
import vn.insee.retailer.controller.dto.TopicDTO;
import vn.insee.retailer.webhook.ZaloEventManager;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LightingQuizPromotionService {
    private static final Logger LOGGER = LogManager.getLogger(LightingQuizPromotionService.class);
    @Autowired
    private LightingQuizPromotionRepository lzQuizPromotionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ZaloEventManager zaloEventManager;

    @Autowired
    private ObjectMapper objectMapper;

    public LightingQuizPromotionEntity get(int id) {
        return lzQuizPromotionRepository.getOne(id);
    }

    public void sendMsgBeforeStart5Min(int promotionId) throws JsonProcessingException {
        Optional<LightingQuizPromotionEntity> optionalLightingQuizPromotion = lzQuizPromotionRepository.findById(promotionId);
        if (optionalLightingQuizPromotion.isPresent()) {
            LightingQuizPromotionEntity lightingQuizPromotionEntity = optionalLightingQuizPromotion.get();
            TopicDTO topicUpComing = getTopicUpComing(lightingQuizPromotionEntity);
            List<UserEntity> userEntities = userService.findByLocation(lightingQuizPromotionEntity.getLocations());
            for (UserEntity userEntity: userEntities) {
                User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
                Before5MinMessage sendBefore5MinMessage = new Before5MinMessage(user, topicUpComing.getTitle(),
                        lightingQuizPromotionEntity.getId(), topicUpComing.getTimeStart());
                sendBefore5MinMessage.send();
            }
        }
    }

    public void start(int promotionId) throws JsonProcessingException {
        Optional<LightingQuizPromotionEntity> optionalLightingQuizPromotion = lzQuizPromotionRepository.findById(promotionId);
        if (optionalLightingQuizPromotion.isPresent()) {
            LightingQuizPromotionEntity lightingQuizPromotionEntity = optionalLightingQuizPromotion.get();
            TopicDTO topic = getTopicUpComing(lightingQuizPromotionEntity);
            List<UserEntity> userEntities = userService.findByLocation(lightingQuizPromotionEntity.getLocations());
            for (UserEntity userEntity: userEntities) {
                User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
                LightingQuizScript lightingQuizScript = new LightingQuizScript(user);
                lightingQuizScript.start(lightingQuizPromotionEntity, topic);
            }
        }
    }


    public LightingQuizPromotionEntity get() throws Exception {
        List<LightingQuizPromotionEntity> promotionList = lzQuizPromotionRepository.findAll();
        if (promotionList == null) {
            throw new Exception("not found lz promotion ");
        }
        return getPromotionUpComing(promotionList);
    }

    public LightingQuizPromotionEntity getUpComing(int city) throws Exception {
        List<LightingQuizPromotionEntity> promotionList = lzQuizPromotionRepository.findAll();
        if (promotionList == null) {
            throw new Exception("not found lz promotion ");
        }
        promotionList = promotionList.stream().filter(promotion -> promotion.getLocations().contains(city))
                .collect(Collectors.toList());
        if (promotionList == null) {
            throw new Exception("not found lz promotion for city: " + city);
        }
        return getPromotionUpComing(promotionList);
    }

    public TopicDTO getTopicUpComing(LightingQuizPromotionEntity lightingQuizPromotionEntity) throws JsonProcessingException {
        List<TopicDTO> listTopicDTO = getListTopicDTO(lightingQuizPromotionEntity);
        if (listTopicDTO == null || listTopicDTO.isEmpty()) {
            return null;
        }
        return getTopicUpComing(listTopicDTO);
    }

    private LightingQuizPromotionEntity getPromotionUpComing(List<LightingQuizPromotionEntity> lightingQuizPromotionEntities) throws JsonProcessingException {
        Map<TopicDTO, LightingQuizPromotionEntity> map = new HashMap<>();
        for (LightingQuizPromotionEntity lightingQuizPromotionEntity: lightingQuizPromotionEntities) {
            TopicDTO topicUpComing = getTopicUpComing(lightingQuizPromotionEntity);
            if (topicUpComing != null) {
                map.put(topicUpComing, lightingQuizPromotionEntity);
            }
        }
        TopicDTO topicUpComing = getTopicUpComing(new ArrayList<>(map.keySet()));
        if (topicUpComing != null) {
            return map.get(topicUpComing);
        }
        return null;
    }

    private TopicDTO getTopicUpComing(List<TopicDTO> listTopicDTO) {
        long currentTime = System.currentTimeMillis();
        Optional<TopicDTO> opUpComing = listTopicDTO.stream()
                .filter(topic -> topic.getTimeEnd() >= currentTime)
                .sorted((t1, t2) -> (int) (t1.getTimeStart() - t2.getTimeStart()))
                .findFirst();
        if (opUpComing.isPresent()) {
            return opUpComing.get();
        }
        return null;
    }

    private List<TopicDTO> getListTopicDTO(LightingQuizPromotionEntity entity) throws JsonProcessingException {
        String strTopic = entity.getTopics();
        if (strTopic == null || strTopic.isEmpty()) {
            strTopic = "[]";
        }
        return objectMapper.readValue(strTopic, new TypeReference<List<TopicDTO>>() {
        });
    }

}
