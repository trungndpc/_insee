package vn.insee.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.MatchFootballEntity_;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.PostEntity_;

@Component
public class MatchFootballSpecification {

    public Specification<MatchFootballEntity> isNotStatus(int status) {
        return (root, query, builder) -> builder.notEqual(root.get(MatchFootballEntity_.status), status);
    }

    public Specification<MatchFootballEntity> isStatus(int status) {
        return (root, query, builder) -> builder.equal(root.get(MatchFootballEntity_.status), status);
    }


    public Specification<MatchFootballEntity> isSeason(String season) {
        return (root, query, builder) -> builder.equal(root.get(MatchFootballEntity_.season), season);
    }

}
