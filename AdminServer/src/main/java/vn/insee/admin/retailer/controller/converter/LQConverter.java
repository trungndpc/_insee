package vn.insee.admin.retailer.controller.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.dto.LQPromotionDTO;
import vn.insee.admin.retailer.controller.dto.QuestionDTO;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.controller.form.LightingQuizTopicForm;
import vn.insee.admin.retailer.controller.form.PromotionForm;
import vn.insee.admin.retailer.controller.form.QuestionForm;
import vn.insee.admin.retailer.mapper.Mapper;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class LQConverter {
    private static final Logger LOGGER = LogManager.getLogger(LQConverter.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    public LightingQuizPromotionEntity convert2Entity(PromotionForm promotionForm) {
        return mapper.map(promotionForm, LightingQuizPromotionEntity.class);
    }

    public LightingQuizPromotionEntity convert2Entity(LightingQuizPromotionEntity entity,
                                                      LightingQuizTopicForm quizTopicForm) throws JsonProcessingException {
        String strTopic = entity.getTopics();
        if (strTopic == null || strTopic.isEmpty()) {
            strTopic = "[]";
        }
        List<TopicDTO> topicDTOS = objectMapper.readValue(strTopic, new TypeReference<List<TopicDTO>>() {
        });
        Iterator<TopicDTO> itr = topicDTOS.iterator();
        while (itr.hasNext()) {
            TopicDTO next = itr.next();
            if (next.getId().equals(quizTopicForm.getId())) {
                itr.remove();
            }
        }

        TopicDTO topic = mapper.map(quizTopicForm, TopicDTO.class);
        topicDTOS.add(topic);
        entity.setTopics(objectMapper.writeValueAsString(topicDTOS));
        return entity;
    }

    public LightingQuizPromotionEntity convert2Entity(LightingQuizPromotionEntity entity,
                                                      String topicId, QuestionForm quizTopicForm) throws JsonProcessingException {
        String strTopic = entity.getTopics();
        if (strTopic == null) {
            strTopic = "[]";
        }
        List<TopicDTO> topicDTOS = objectMapper.readValue(strTopic, new TypeReference<List<TopicDTO>>() {
        });
        topicDTOS.stream().forEach(topic -> {
            if (topic.getId().equals(topicId)) {
                List<QuestionDTO> questions = topic.getQuestions();
                if (questions == null) {
                    questions = new ArrayList<>();
                }
                Iterator<QuestionDTO> itr = questions.iterator();
                while (itr.hasNext()) {
                    QuestionDTO next = itr.next();
                    if (next.getId().equals(quizTopicForm.getId())) {
                        itr.remove();
                    }
                };
                QuestionDTO questionDTO = mapper.map(quizTopicForm, QuestionDTO.class);
                questions.add(questionDTO);
                topic.setQuestions(questions);
            }
        });
        entity.setTopics(objectMapper.writeValueAsString(topicDTOS));
        return entity;
    }

    public LQPromotionDTO convert2DTO(LightingQuizPromotionEntity promotionEntity) {
        return mapper.map(promotionEntity, LQPromotionDTO.class);
    }

    public TopicDTO getTopicDTO(LightingQuizPromotionEntity promotionEntity, String topicId) throws JsonProcessingException {
        String strTopic = promotionEntity.getTopics();
        if (strTopic == null) {
            strTopic = "[]";
        }
        List<TopicDTO> topicDTOS = objectMapper.readValue(strTopic, new TypeReference<List<TopicDTO>>() {
        });
        Optional<TopicDTO> any = topicDTOS.stream().filter(t -> t.getId().equals(topicId)).findAny();
        if (any.isPresent()) {
            return any.get();
        }
        return null;
    }

}
