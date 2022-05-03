package vn.insee.admin.retailer.woker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.common.UserStatus;
import vn.insee.admin.retailer.controller.dto.TopicDTO;
import vn.insee.admin.retailer.message.Before5MinMessage;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.service.*;
import vn.insee.admin.retailer.woker.task.Notify2PredictMatchFootballTask;
import vn.insee.admin.retailer.woker.task.TopicTask;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class Notify2PredictMatchFootballWorker {
    private static final Logger LOGGER = LogManager.getLogger(Notify2PredictMatchFootballWorker.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FootballPromotionService promotionService;

    @Autowired
    private MatchFootballService matchFootballService;

    @Autowired
    private PredictFootballFormService predictFootballFormService;


    @Job(name = "NOTY_UPCOMING_TOPIC", retries = 1)
    public void execute(Notify2PredictMatchFootballTask task) {
        try{
            int promotionId = task.getPromotionId();
            int matchId = task.getMatchId();
            PredictFootballPromotionEntity promotionEntity = promotionService.get(promotionId);
            List<PredictMatchFootballFormEntity> predicts = predictFootballFormService.findByMatch(matchId);
            List<UserEntity> userEntities = userService.findBy(promotionEntity.getCityIds(), promotionEntity.getDistrictIds(), UserStatus.APPROVED);
            if (predicts != null || !predicts.isEmpty()) {
                List<Integer> uidHasPredict = predicts.stream().map(predict -> predict.getUserId()).collect(Collectors.toList());
                userEntities = userEntities.stream().filter(userEntity -> !uidHasPredict.contains(userEntity.getId()))
                        .collect(Collectors.toList());
            }
            //call api
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


}
