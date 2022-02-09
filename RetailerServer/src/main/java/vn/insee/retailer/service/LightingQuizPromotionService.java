package vn.insee.retailer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.repository.LightingQuizPromotionRepository;
import vn.insee.retailer.bot.User;
import vn.insee.retailer.bot.message.SendBefore5MinMessage;
import vn.insee.retailer.bot.script.LightingQuizScript;
import vn.insee.retailer.webhook.ZaloEventManager;

import java.util.List;
import java.util.Optional;
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

    public void sendMsgBeforeStart5Min(int promotionId) {
        Optional<LightingQuizPromotionEntity> optionalLightingQuizPromotion = lzQuizPromotionRepository.findById(promotionId);
        if (optionalLightingQuizPromotion.isPresent()) {
            LightingQuizPromotionEntity lightingQuizPromotionEntity = optionalLightingQuizPromotion.get();
            List<UserEntity> userEntities = userService.findByLocation(lightingQuizPromotionEntity.getLocations());
            for (UserEntity userEntity: userEntities) {
                User user = new User();
                user.setUid(userEntity.getId());
                user.setFollowerId(userEntity.getFollowerId());
                user.setName(userEntity.getName());
                SendBefore5MinMessage sendBefore5MinMessage = new SendBefore5MinMessage(user, lightingQuizPromotionEntity.getTitle(),
                        lightingQuizPromotionEntity.getId());
                sendBefore5MinMessage.send();
            }
        }
    }

    public void sendMsgToStart(int promotionId) throws JsonProcessingException {
        Optional<LightingQuizPromotionEntity> optionalLightingQuizPromotion = lzQuizPromotionRepository.findById(promotionId);
        if (optionalLightingQuizPromotion.isPresent()) {
            LightingQuizPromotionEntity lightingQuizPromotionEntity = optionalLightingQuizPromotion.get();
            List<UserEntity> userEntities = userService.findByLocation(lightingQuizPromotionEntity.getLocations());
            for (UserEntity userEntity: userEntities) {
                User user = new User();
                user.setUid(userEntity.getId());
                user.setFollowerId(userEntity.getFollowerId());
                user.setName(userEntity.getName());
                LightingQuizScript lightingQuizScript = new LightingQuizScript(user);
                lightingQuizScript.start(promotionId);
                zaloEventManager.put(user.getUid(), lightingQuizScript.getSession());
            }
        }
    }


    public LightingQuizPromotionEntity getUpComing() throws Exception {
        List<LightingQuizPromotionEntity> promotionList = lzQuizPromotionRepository.findAll();
        if (promotionList == null) {
            throw new Exception("not found lz promotion ");
        }
        //Todo
        return promotionList.get(0);
    }

    public List<LightingQuizPromotionEntity> findByLocation(int location) {
        List<LightingQuizPromotionEntity> all = lzQuizPromotionRepository.findAll();
        return all.stream().filter(a -> a.getLocations().contains(location)).collect(Collectors.toList());
    }

}
