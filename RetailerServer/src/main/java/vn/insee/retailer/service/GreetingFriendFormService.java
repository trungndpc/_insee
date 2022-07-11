package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusStockForm;
import vn.insee.common.type.TypePromotion;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.repository.GreetingFriendFormRepository;
import vn.insee.jpa.repository.StockFormRepository;
import vn.insee.service.GreetingFriendFormServiceCommon;

@Service
public class GreetingFriendFormService extends GreetingFriendFormServiceCommon {

    public GreetingFriendFormEntity create(GreetingFriendFormEntity entity) {
        entity.setStatus(StatusStockForm.INIT);
        entity.setType(TypePromotion.GREETING_FRIEND);
        entity = repository.saveAndFlush(entity);
        return entity;
    }

}
