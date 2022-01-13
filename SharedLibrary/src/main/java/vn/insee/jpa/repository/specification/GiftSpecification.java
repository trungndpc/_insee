package vn.insee.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.GiftEntity;
import vn.insee.jpa.entity.GiftEntity_;

import java.util.List;

@Component
public class GiftSpecification {

    public Specification<Object> init() {
        return  Specification.where(null);
    }

    public Specification<GiftEntity> giftInConstruction(List<Integer> constructions) {
        return (root, query, builder) -> root.get(GiftEntity_.constructionId).in(constructions);
    }

    public Specification<GiftEntity> giftIsStatus(Integer status) {
        return (root, query, builder) ->
                builder.equal(root.get(GiftEntity_.status), status);
    }

    public Specification<GiftEntity> giftIsType(Integer type) {
        return (root, query, builder) ->
                builder.equal(root.get(GiftEntity_.type), type);
    }



}
