package vn.insee.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.PromotionEntity_;

import java.util.List;

@Component
public class PromotionSpecification {
    public Specification<PromotionEntity> inTypes(List<Integer> types) {
        return (root, query, builder) ->
                root.get(PromotionEntity_.type).in(types);
    }
}
