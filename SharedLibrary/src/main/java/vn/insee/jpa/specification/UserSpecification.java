package vn.insee.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.PromotionEntity_;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.UserEntity_;

import java.util.List;

@Component
public class UserSpecification {

    public Specification<UserEntity> isStatus(int status) {
        return (root, query, builder) ->
                builder.equal(root.get(UserEntity_.status), status);
    }

    public Specification<UserEntity> isLocation(int location) {
        return (root, query, builder) ->
                builder.equal(root.get(UserEntity_.cityId), location);
    }

    public Specification<UserEntity> likePhone(String phone) {
        return (root, query, builder) ->
                builder.like(root.get(UserEntity_.phone), phone);
    }

    public Specification<UserEntity> likeName(String name) {
        return (root, query, builder) ->
                builder.like(root.get(UserEntity_.name), name);
    }

}
