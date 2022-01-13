package vn.insee.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.RetailerEntity;
import vn.insee.jpa.entity.RetailerEntity_;

@Component
public class RetailerSpecification {
    public Specification<RetailerEntity> init() {
        return  Specification.where(null);
    }

    public Specification<RetailerEntity> isDistrict(Integer status) {
        return (root, query, builder) ->
                builder.equal(root.get(RetailerEntity_.district), status);
    }

    public Specification<RetailerEntity> isCity(Integer city) {
        return (root, query, builder) ->
                builder.equal(root.get(RetailerEntity_.city), city);
    }

}
