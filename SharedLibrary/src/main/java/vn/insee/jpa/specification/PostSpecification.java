package vn.insee.jpa.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.PostEntity_;

@Component
public class PostSpecification {

    public Specification<PostEntity> isNotStatus(int status) {
        return (root, query, builder) -> builder.notEqual(root.get(PostEntity_.status), status);
    }
}
