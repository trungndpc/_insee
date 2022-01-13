package vn.insee.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.ConstructionEntity;
import vn.insee.jpa.entity.ConstructionEntity_;

import java.util.List;

@Component
public class ConstructionSpecification {

    public Specification<ConstructionEntity> init() {
        return  Specification.where(null);
    }

    public Specification<ConstructionEntity> constructionIsStatus(Integer status) {
        return (root, query, builder) ->
                builder.equal(root.get(ConstructionEntity_.status), status);
    }

    public Specification<ConstructionEntity> constructionInType(List<Integer> types) {
        return (root, query, builder) ->
                root.get(ConstructionEntity_.type).in(types);
    }

    public Specification<ConstructionEntity> constructionInPromotion(List<Integer> promotionIds) {
        return (root, query, builder) ->
                root.get(ConstructionEntity_.promotionId).in(promotionIds);
    }

}
