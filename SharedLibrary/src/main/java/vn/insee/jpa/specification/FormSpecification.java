package vn.insee.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.FormEntity_;
import vn.insee.jpa.entity.form.StockFormEntity;
import vn.insee.jpa.entity.form.StockFormEntity_;

import java.util.List;

@Component
public class FormSpecification {
    public Specification<FormEntity> isStatus(int status) {
        return (root, query, builder) ->
                builder.equal(root.get(FormEntity_.status), status);
    }

    public Specification<FormEntity> isPromotion(int promotionId) {
        return (root, query, builder) ->
                builder.equal(root.get(FormEntity_.promotionId), promotionId);
    }

    public Specification<FormEntity> inPromotions(List<Integer> promotions) {
        return (root, query, builder) ->
                root.get(FormEntity_.promotionId).in(promotions);
    }
}
