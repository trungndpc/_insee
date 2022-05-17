package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.controller.converter.LQConverter;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.message.football.SuccessGroupStageMessage;
import vn.insee.admin.retailer.service.AccumulationService;
import vn.insee.admin.retailer.service.LightingQuizFormService;
import vn.insee.admin.retailer.service.LightingQuizPromotionService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.admin.retailer.woker.task.ReportGroupStageSeagameTask;
import vn.insee.admin.retailer.woker.task.TopicTask;
import vn.insee.common.status.StatusTopicLightingQuizPromotion;
import vn.insee.jpa.entity.AccumulationEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;

import java.util.List;

@Component
public class ReportGroupStageSeagameWorker {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AccumulationService accumulationService;

    @Autowired
    private UserService userService;



    @Job(name = "REPORT_GROUP_STAGE_SEAGAME", retries = 1)
    public void execute(ReportGroupStageSeagameTask task) {
        try {
            reportGroupStage();
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void reportGroupStage() {
        List<AccumulationEntity> accumulationEntities = accumulationService.getAll();
        accumulationEntities.stream().filter( f -> f.getPoint() > 0)
                .forEach(accumulationEntity -> {
                    sendSuccessGroupStageMessage(accumulationEntity.getUid(), accumulationEntity.getPoint());
                });
    }

    private void sendSuccessGroupStageMessage(int uid, int point) {
        try{
            UserEntity userEntity = userService.findById(uid);
            User user = new User();
            user.setUid(uid);
            user.setFollowerId(userEntity.getFollowerId());
            user.setName(userEntity.getName());
//            if (uid == 505) {
                new SuccessGroupStageMessage(user, point).send();
//            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
