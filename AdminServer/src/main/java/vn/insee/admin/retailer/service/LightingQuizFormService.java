package vn.insee.admin.retailer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.message.TopLeaderBoardLightingQuizMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.common.status.StatusLightingQuizForm;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.jpa.repository.LightingQuizFormRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LightingQuizFormService {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private LightingQuizFormRepository lightingQuizFormRepository;

    @Autowired
    private UserService userService;

    public Page<LightingQuizFormEntity> findByPromotionIdAndTopicId(int promotionId, String topicId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        List<LightingQuizFormEntity> quizFormEntities = lightingQuizFormRepository.findByPromotionIdAndTopicId(promotionId, topicId);
        quizFormEntities = ranking(quizFormEntities);
        if (quizFormEntities != null && !quizFormEntities.isEmpty()) {
            List<LightingQuizFormEntity> list = ranking(quizFormEntities);
            final int start = (int) pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), list.size());
            return new PageImpl<>(list.subList(start, end), pageable, list.size());
        }
        return Page.empty();
    }

    public LightingQuizFormEntity get(int id) {
        return lightingQuizFormRepository.getOne(id);
    }

    public boolean updateStatus(int id, int status) throws Exception {
        LightingQuizFormEntity entity = lightingQuizFormRepository.getOne(id);
        if (entity == null) {
            throw new Exception("lighting quiz form is not exit id: " + id);
        }
        entity.setStatus(status);
        lightingQuizFormRepository.saveAndFlush(entity);
        return true;
    }

    public void summary2Ranking(int promotionId, String topicId, String title) {
        List<LightingQuizFormEntity> lightingQuizFormEntityList = lightingQuizFormRepository.findByPromotionIdAndTopicId(promotionId, topicId);
        lightingQuizFormEntityList = ranking(lightingQuizFormEntityList);
        lightingQuizFormEntityList.forEach(lightingQuizFormEntity -> {
            lightingQuizFormEntity.setStatus(StatusLightingQuizForm.APPROVED);
        });

        lightingQuizFormEntityList.stream().limit(5).forEach(lightingQuizFormEntity -> {
            UserEntity userEntity = userService.findById(lightingQuizFormEntity.getUserId());
            User user = new User(userEntity.getId(), userEntity.getFollowerId(), userEntity.getName());
            TopLeaderBoardLightingQuizMessage quizMessage = new TopLeaderBoardLightingQuizMessage(user, title);
            quizMessage.send();
        });
        lightingQuizFormRepository.saveAll(lightingQuizFormEntityList);
    }

    private List<LightingQuizFormEntity> ranking(List<LightingQuizFormEntity> quizFormEntities) {
        return quizFormEntities.stream().sorted((entity1, entity2) -> {
            int p = entity2.getPoint() - entity1.getPoint();
            if (p == 0) {
                JSONObject json1 = new JSONObject(entity1.getJsonDetail());
                JSONObject json2 = new JSONObject(entity2.getJsonDetail());

                long duration1 = json1.getLong("timeEnd") - json1.getLong("timeStart");
                long duration2 = json2.getLong("timeEnd") - json2.getLong("timeStart");
                return (int) (duration1 - duration2);
            }
            return p;
        }).collect(Collectors.toList());
    }

    public List<LightingQuizFormEntity> findByPromotionId(int promotionId) {
        return lightingQuizFormRepository.findByPromotionId(promotionId);
    }
}
