package vn.insee.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.CustomerEntity;
import vn.insee.jpa.entity.CustomerEntity_;

import java.util.List;

@Component
public class CustomerSpecification {


    public Specification<CustomerEntity> init() {
        return  Specification.where(null);
    }


    public Specification<CustomerEntity> customerIsStatus(Integer status) {
        return (root, query, builder) ->
                builder.equal(root.get(CustomerEntity_.status), status);
    }

    public Specification<CustomerEntity> customerIsLocation(Integer location) {
        return (root, query, builder) ->
                builder.equal(root.get(CustomerEntity_.mainAreaId), location);
    }

    public Specification<CustomerEntity> customerLikePhone(String phone) {
        return (root, query, builder) ->
                builder.like(root.get(CustomerEntity_.phone), phone);
    }

    public Specification<CustomerEntity> inListUserId(List<Integer> uids) {
        return (root, query, builder) ->
                root.get(CustomerEntity_.userId).in(uids);
    }

    public Specification notNullPoint() {
        return (root, query, builder) ->
                root.get(CustomerEntity_.point).isNotNull();
    }

}
