package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusGift;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.GiftEntity;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.repository.GiftRepository;
import vn.insee.jpa.specification.GiftSpecification;

@Service
public class GiftService {
    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private GiftSpecification giftSpecification;

    public GiftEntity create(GiftEntity form) {
        return giftRepository.saveAndFlush(form) ;
    }

    public long count(Integer status) {
        Specification<GiftEntity> specs =  Specification.where(null);
        if (status != null) {
            specs = specs.and(giftSpecification.isStatus(status));
        }
        return giftRepository.count(specs);
    }

}
