package vn.insee.retailer.service;

import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusGreetingFriendForm;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.service.GreetingFriendFormServiceCommon;

@Service
public class GreetingFriendFormService extends GreetingFriendFormServiceCommon {

    public GreetingFriendFormEntity create(GreetingFriendFormEntity entity) {
        entity.setStatus(StatusGreetingFriendForm.WAITING_SUBMIT_FORM);
        entity.setType(TypePromotion.GREETING_FRIEND);
        entity = repository.saveAndFlush(entity);
        return entity;
    }

    public GreetingFriendFormEntity submit(GreetingFriendFormEntity entity) throws Exception {
        GreetingFriendFormEntity exitForm = repository.findByUserIdAndPromotionId(entity.getUserId(), entity.getPromotionId());
        if (exitForm == null) {
            throw new Exception("form not exit for user: " + entity.getUserId() + " and promotionId: " + entity.getPromotionId());
        }
        exitForm.setTime(System.currentTimeMillis());
        exitForm.setBags(entity.getBags());
        exitForm.setJsonImage(entity.getJsonImage());
        exitForm.setStatus(StatusGreetingFriendForm.WAITING_APPROVAL);
        exitForm = repository.saveAndFlush(exitForm);
        return exitForm;
    }

}
