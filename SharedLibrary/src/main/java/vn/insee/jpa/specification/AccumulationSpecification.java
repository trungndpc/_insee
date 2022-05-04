package vn.insee.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.*;

@Component
public class AccumulationSpecification {

    public Specification<AccumulationEntity> isPromotion(int promotionId) {
        return (root, query, builder) -> builder.equal(root.get(AccumulationEntity_.promotionId), promotionId);
    }

    public Specification<AccumulationEntity> isType(int type) {
        return (root, query, builder) -> builder.equal(root.get(AccumulationEntity_.type), type);
    }

    public Specification<AccumulationEntity> isUserId(int uid) {
        return (root, query, builder) -> builder.equal(root.get(AccumulationEntity_.uid), uid);
    }

}
