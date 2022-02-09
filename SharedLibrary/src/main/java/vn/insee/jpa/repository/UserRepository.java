package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.UserEntity;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByPhone(String phone);
    UserEntity findByZaloId(String zaloId);
    UserEntity findByFollowerId(String followerId);
    Page<UserEntity> findAll(Specification spec, Pageable pageable);
    List<UserEntity> findByCityIdIn(List<Integer> locations);
}
