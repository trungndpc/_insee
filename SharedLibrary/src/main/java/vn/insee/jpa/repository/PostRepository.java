package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.PostEntity;


public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    Page<PostEntity> findAll(Specification spec, Pageable pageable);

}
