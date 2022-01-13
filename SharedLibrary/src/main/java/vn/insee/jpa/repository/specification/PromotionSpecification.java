package vn.insee.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.common.status.PromotionStatus;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.PromotionEntity_;

import java.util.List;

@Component
public class PromotionSpecification {
    public Specification<PromotionEntity> init() {
        return  Specification.where(null);
    }

    public Specification<PromotionEntity> promotionIsStatus(Integer status) {
        return (root, query, builder) ->
                builder.equal(root.get(PromotionEntity_.status), status);
    }

    public Specification<PromotionEntity> promotionIsSeason(int season) {
        return (root, query, builder) ->
                builder.equal(root.get(PromotionEntity_.season), season);
    }

    public Specification<PromotionEntity> promotionNotDeleted() {
        return (root, query, builder) ->
                builder.notEqual(root.get(PromotionEntity_.status), PromotionStatus.DELETED.getStatus());
    }

    public Specification<PromotionEntity> promotionInTypeGift(List<Integer> gifts) {
        return (root, query, builder) ->
                root.get(PromotionEntity_.typeGift).in(gifts);
    }

    public Specification<PromotionEntity> promotionIsType(Integer type) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(PromotionEntity_.typePromotion), type));
    }


}
