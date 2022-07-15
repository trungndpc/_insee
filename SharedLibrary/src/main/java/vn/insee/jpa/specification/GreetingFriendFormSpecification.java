package vn.insee.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity_;

import java.util.List;

@Component
public class GreetingFriendFormSpecification {
    public Specification<GreetingFriendFormEntity> isStatus(int status) {
        return (root, query, builder) ->
                builder.equal(root.get(GreetingFriendFormEntity_.status), status);
    }

    public Specification<GreetingFriendFormEntity> isPromotion(int promotionId) {
        return (root, query, builder) ->
                builder.equal(root.get(GreetingFriendFormEntity_.promotionId), promotionId);
    }

    public Specification<GreetingFriendFormEntity> inPromotions(List<Integer> promotions) {
        return (root, query, builder) ->
                root.get(GreetingFriendFormEntity_.promotionId).in(promotions);
    }
}
