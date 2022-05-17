package vn.insee.admin.retailer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.insee.admin.retailer.common.BaseResponse;
import vn.insee.admin.retailer.common.ErrorCode;
import vn.insee.admin.retailer.controller.converter.MatchFootballConverter;
import vn.insee.admin.retailer.controller.dto.MatchFootballDTO;
import vn.insee.admin.retailer.controller.dto.PageDTO;
import vn.insee.admin.retailer.message.User;
import vn.insee.admin.retailer.message.football.SuccessPredictMatchMessage;
import vn.insee.admin.retailer.service.*;
import vn.insee.common.status.MatchFootballStatus;
import vn.insee.common.status.PredictMatchFootballStatus;
import vn.insee.common.type.TypeAccumulation;
import vn.insee.jpa.entity.AccumulationEntity;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/match-football")
public class MatchFootballController {
    private static final Logger LOGGER = LogManager.getLogger(MatchFootballController.class);

    @Autowired
    private MatchFootballService matchFootballService;

    @Autowired
    private MatchFootballConverter converter;

    @Autowired
    private FootballPromotionService promotionService;

    @Autowired
    private PredictFootballFormService predictFootballFormService;

    @Autowired
    private AccumulationService accumulationService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/list")
    public ResponseEntity<BaseResponse> find(@RequestParam(required = true) int promotionId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        BaseResponse response = new BaseResponse();
        try{
            PredictFootballPromotionEntity promotionEntity = promotionService.get(promotionId);
            if (promotionEntity == null) {
                throw new Exception("promotion is not exit!" + promotionId);
            }
            Page<MatchFootballEntity> entities = matchFootballService.find(promotionEntity.getSeason(), page, pageSize);
            PageDTO<MatchFootballDTO> dto =
                    converter.convertToPageDTO(entities);
            response.setData(dto);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/update-result-match")
    public ResponseEntity<BaseResponse> find(@RequestParam(required = true) int promotionId,
                                             @RequestParam(required = true) int matchId,
                                             @RequestParam(required = true) int scoreTeamOne,
                                             @RequestParam(required = true) int scoreTeamTwo) {
        BaseResponse response = new BaseResponse();
        try{
            MatchFootballEntity matchFootball = matchFootballService.getById(matchId);
            matchFootball.setTeamOneScore(scoreTeamOne);
            matchFootball.setTeamTwoScore(scoreTeamTwo);
            matchFootball.setStatus(MatchFootballStatus.DONE);
            matchFootball = matchFootballService.createOrUpdate(matchFootball);
            completeMatchWithTypeOne(promotionId, matchFootball);
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    private void completeMatchWithTypeOne(int promotionId, MatchFootballEntity match) {
        List<PredictMatchFootballFormEntity> predicts = predictFootballFormService.findByMatch(match.getId());
        if (predicts != null && !predicts.isEmpty()) {
            final int scoreTeamOne = match.getTeamOneScore();
            final int scoreTeamTwo = match.getTeamTwoScore();
            final int teamWin = scoreTeamOne == scoreTeamTwo ? 0 : (scoreTeamOne > scoreTeamTwo ? 1 : 2);
            predicts.forEach(entity -> {
                if (teamWin == entity.getTeamWin()) {
                    entity.setStatus(PredictMatchFootballStatus.CORRECT_TEAM);
                }else {
                    entity.setStatus(PredictMatchFootballStatus.WRONG);
                }
            });
            predicts = predictFootballFormService.save(predicts);
            predicts.stream().filter(predict -> predict.getStatus() == PredictMatchFootballStatus.CORRECT_TEAM)
                    .forEach(entity -> {
                        AccumulationEntity accumulationEntity = accumulationService.findByUidAndPromotionAndType(entity.getUserId(),
                                promotionId, TypeAccumulation.PREDICT_FOOTBALL_COLLECT_POINT_TYPE_ONE);
                        if (accumulationEntity == null) {
                            accumulationEntity = new AccumulationEntity();
                            accumulationEntity.setType(TypeAccumulation.PREDICT_FOOTBALL_COLLECT_POINT_TYPE_ONE);
                            accumulationEntity.setUid(entity.getUserId());
                            accumulationEntity.setPromotionId(promotionId);
                            accumulationEntity.setPoint(0);
                        }
                        accumulationEntity.setPoint(accumulationEntity.getPoint() + 1);
                        accumulationService.createOrUpdate(accumulationEntity);
                        // khong thong bao diem nua
//                        sendMsgSuccessPredictMatch(entity.getUserId(), match, accumulationEntity.getPoint());
                    });
        }
    }

    private void sendMsgSuccessPredictMatch(int uid, MatchFootballEntity match, int point) {
        try{
            UserEntity userEntity = userService.findById(uid);
            User user = new User();
            user.setUid(uid);
            user.setFollowerId(userEntity.getFollowerId());
            user.setName(userEntity.getName());
            new SuccessPredictMatchMessage(user, match.getTeamOne(), match.getTeamTwo(), point).send();
        }catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
