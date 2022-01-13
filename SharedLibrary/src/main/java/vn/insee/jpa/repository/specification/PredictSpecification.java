package vn.insee.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.PredictEntity;
import vn.insee.jpa.entity.PredictEntity_;

@Component
public class PredictSpecification {

    public Specification<PredictEntity> init() {
        return  Specification.where(null);
    }

    public Specification<PredictEntity> isStatus(Integer status) {
        return (root, query, builder) ->
                builder.equal(root.get(PredictEntity_.status), status);
    }

}
