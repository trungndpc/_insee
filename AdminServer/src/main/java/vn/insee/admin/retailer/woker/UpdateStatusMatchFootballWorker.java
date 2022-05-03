package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.common.UserStatus;
import vn.insee.admin.retailer.service.FootballPromotionService;
import vn.insee.admin.retailer.service.MatchFootballService;
import vn.insee.admin.retailer.service.PredictFootballFormService;
import vn.insee.admin.retailer.service.UserService;
import vn.insee.admin.retailer.woker.task.Notify2PredictMatchFootballTask;
import vn.insee.admin.retailer.woker.task.UpdateStatusMatchTask;
import vn.insee.common.status.MatchFootballStatus;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UpdateStatusMatchFootballWorker {
    private static final Logger LOGGER = LogManager.getLogger(UpdateStatusMatchFootballWorker.class);

    @Autowired
    private MatchFootballService matchFootballService;


    @Job(name = "NOTY_UPCOMING_TOPIC", retries = 1)
    public void execute(UpdateStatusMatchTask task) {
        try{
            MatchFootballEntity matchFootballEntity = matchFootballService.getById(task.getMatchId());
            matchFootballEntity.setStatus(MatchFootballStatus.PROCESSING);
            matchFootballService.createOrUpdate(matchFootballEntity);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


}
