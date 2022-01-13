package vn.insee.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.MatchEntity;
import vn.insee.jpa.entity.MatchEntity_;

@Component
public class MatchSpecification {
    public Specification<Object> init() {
        return  Specification.where(null);
    }

    public Specification<MatchEntity> isStatus(Integer status) {
        return (root, query, builder) ->
                builder.equal(root.get(MatchEntity_.status), status);
    }

    public Specification<MatchEntity> isSeason(Integer season) {
        return (root, query, builder) ->
                builder.equal(root.get(MatchEntity_.seasonId), season);
    }

}
