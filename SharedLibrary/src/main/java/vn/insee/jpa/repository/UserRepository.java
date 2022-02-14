package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.custom.UserRepositoryCustom;
import vn.insee.jpa.entity.UserEntity;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity>, UserRepositoryCustom {
    UserEntity findByPhone(String phone);
    UserEntity findByZaloId(String zaloId);
    UserEntity findByFollowerId(String followerId);
    Page<UserEntity> findAll(Specification spec, Pageable pageable);
    List<UserEntity> findByCityIdIn(List<Integer> locations);
}
