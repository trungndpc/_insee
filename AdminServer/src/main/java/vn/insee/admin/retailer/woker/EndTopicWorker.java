package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.converter.LQConverter;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.service.LightingQuizFormService;
import vn.insee.admin.retailer.service.LightingQuizPromotionService;
import vn.insee.admin.retailer.woker.task.TopicTask;
import vn.insee.common.status.StatusTopicLightingQuizPromotion;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;

@Component
public class EndTopicWorker {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private LightingQuizPromotionService promotionService;

    @Autowired
    private LightingQuizFormService lightingQuizFormService;

    @Autowired
    private LQConverter lqConverter;


    @Job(name = "END_TOPIC", retries = 1)
    public void execute(TopicTask task) {
        try {
            promotionService.updateStatusTopic(task.getPromotionId(), task.getTopicId(), StatusTopicLightingQuizPromotion.DONE);
            LightingQuizPromotionEntity lightingQuizPromotionEntity = promotionService.get(task.getPromotionId());
            TopicDTO topicDTO = lqConverter.getTopicDTO(lightingQuizPromotionEntity, task.getTopicId());
            lightingQuizFormService.summary2Ranking(task.getPromotionId(), task.getTopicId(), topicDTO.getTitle());
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
