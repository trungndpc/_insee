package vn.insee.retailer.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusForm;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;
import vn.insee.jpa.repository.GreetingFriendFormRepository;

@Service
public class GreetingFriendFormService {

    @Autowired
    private GreetingFriendFormRepository repository;

    public GreetingFriendFormEntity createOrUpdate(GreetingFriendFormEntity entity) {
        entity.setStatus(StatusForm.INIT);
        return repository.saveAndFlush(entity);
    }

    public GreetingFriendFormEntity activeGreetingNewFriendPromotion(GreetingFriendPromotionEntity promotionEntity,
                                                                     UserEntity userEntity) {
        GreetingFriendFormEntity form = new GreetingFriendFormEntity();
        form.setStatus(StatusForm.INIT);
        form.setUserId(userEntity.getId());
        form.setPromotionId(promotionEntity.getId());
        return createOrUpdate(form);
    }
}
