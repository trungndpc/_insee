package vn.insee.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.entity.MatchFootballEntity_;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity_;

@Component
public class PredictMatchFootballFormSpecification {

    public Specification<PredictMatchFootballFormEntity> isNotStatus(int status) {
        return (root, query, builder) -> builder.notEqual(root.get(PredictMatchFootballFormEntity_.status), status);
    }

    public Specification<PredictMatchFootballFormEntity> isStatus(int status) {
        return (root, query, builder) -> builder.equal(root.get(PredictMatchFootballFormEntity_.status), status);
    }

    public Specification<PredictMatchFootballFormEntity> isSeason(String season) {
        return (root, query, builder) -> builder.equal(root.get(PredictMatchFootballFormEntity_.season), season);
    }

    public Specification<PredictMatchFootballFormEntity> isMatchId(int matchId) {
        return (root, query, builder) -> builder.equal(root.get(PredictMatchFootballFormEntity_.matchId), matchId);
    }

    public Specification<PredictMatchFootballFormEntity> isUid(int uid) {
        return (root, query, builder) -> builder.equal(root.get(PredictMatchFootballFormEntity_.userId), uid);
    }



}
