package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.message.football.ReportMessage;
import vn.insee.admin.retailer.service.AccumulationService;
import vn.insee.admin.retailer.service.PredictFootballFormService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.admin.retailer.woker.task.ReportGroupStageSeagameTask;
import vn.insee.jpa.entity.AccumulationEntity;
import vn.insee.jpa.entity.UserEntity;

import java.util.List;

@Component
public class ReportGroupStageSeagameWorker {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AccumulationService accumulationService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private PredictFootballFormService predictFootballFormService;

    @Job(name = "REPORT_GROUP_STAGE_SEAGAME", retries = 1)
    public void execute(ReportGroupStageSeagameTask task) {
        try {
            reportGroupStage();
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void reportGroupStage() {
        final int PROMOTION_ID_PREDICT_FOOTBALL = 376;
        List<AccumulationEntity> accumulationEntities = accumulationService.findByPromotionId(PROMOTION_ID_PREDICT_FOOTBALL);
        accumulationEntities.stream()
                .filter( f -> f.getPoint() > 0)
                .forEach(accumulationEntity -> {
                    long count = predictFootballFormService.countUserByStatus(accumulationEntity.getUid(), null);
                    sendSuccessGroupStageMessage(accumulationEntity.getUid(), accumulationEntity.getPoint(), (int)count);
                });
    }

    private void sendSuccessGroupStageMessage(int uid, int point, int total) {
        try{
            UserEntity userEntity = userService.findById(uid);
            User user = new User();
            user.setUid(uid);
            user.setFollowerId(userEntity.getFollowerId());
            user.setName(userEntity.getName());
//            if (uid == 505) {
                new ReportMessage(user, point, total).send();
//            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
