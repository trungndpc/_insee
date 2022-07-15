package vn.insee.service;

import org.springframework.beans.factory.annotation.Autowired;
import vn.insee.common.status.StatusGreetingFriendForm;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;
import vn.insee.jpa.repository.GreetingFriendFormRepository;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class GreetingFriendFormServiceCommon {

    @Autowired
    protected GreetingFriendFormRepository repository;

    public List<Integer> checkAndActiveGreetingNewFriendPromotion(UserEntity userEntity,
                                                                  List<GreetingFriendPromotionEntity>  promotionEntities) {
        List<Integer> res = new ArrayList<>();
        promotionEntities.forEach(entity -> {
            GreetingFriendFormEntity form = new GreetingFriendFormEntity();
            form.setStatus(StatusGreetingFriendForm.WAITING_SUBMIT_FORM);
            form.setUserId(userEntity.getId());
            form.setPromotionId(entity.getId());
            form.setType(TypePromotion.GREETING_FRIEND);
            form = repository.saveAndFlush(form);
            res.add(form.getId());
        });
        return res;
    }

    public GreetingFriendFormEntity getForm(int uid, int promotionId) {
        return repository.findByUserIdAndPromotionId(uid, promotionId);
    }

    public GreetingFriendFormEntity get(int id) {
        return repository.getOne(id);
    }

    public GreetingFriendFormEntity saveOrUpdate(GreetingFriendFormEntity entity) {
        return repository.saveAndFlush(entity);
    }

    public List<GreetingFriendFormEntity> findByPromotion(int promotionId) {
        return  repository.findByPromotionId(promotionId);
    }

    public boolean isActive(GreetingFriendFormEntity entity) {
        ZonedDateTime createdTime = entity.getCreatedTime();
        ZonedDateTime now = ZonedDateTime.now();
        Duration between = Duration.between(createdTime, now);
        long days = between.toDays();
        return days <= 30;
    }


}
