package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.service.LightingQuizPromotionService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.common.Constant;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;

import java.util.List;
import java.util.Optional;

@Component
public class StartTopicWorker {
    private static final Logger LOGGER = LogManager.getLogger(StartTopicWorker.class);
    private final RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private LightingQuizPromotionService promotionService;

    public StartTopicWorker() {
        this.restTemplate = new RestTemplate();
    }

    @Job(name = "NOTY_UPCOMING_TOPIC", retries = 1)
    public void execute(NotyUpcomingTopicTask task) {
        try{
            int promotionId = task.getPromotionId();
            String topicId = task.getTopicId();

            LightingQuizPromotionEntity promotionEntity = promotionService.get(promotionId);
            if (promotionEntity == null) {
                throw new Exception("promotion is not exit: " + promotionId);
            }
            List<TopicDTO> listTopicDTO = promotionService.getListTopicDTO(promotionEntity);
            if (listTopicDTO == null) {
                throw new Exception("topics is null " + promotionId);
            }
            Optional<TopicDTO> opTopicDTO = listTopicDTO.stream().filter(topic -> topic.getId().equals(topicId))
                    .findFirst();
            if (!opTopicDTO.isPresent()) {
                throw new Exception("topic is not exits " + topicId);
            }
            ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(Constant.CLIENT_DOMAIN + "/admin/start-topic-lq-game?promotionId= "
                    + promotionEntity.getId() + "&=topicId=" + topicId, String.class);
            LOGGER.info(responseEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
