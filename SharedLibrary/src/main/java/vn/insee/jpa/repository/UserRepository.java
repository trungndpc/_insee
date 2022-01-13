package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.insee.jpa.entity.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByPhone(String phone);
    UserEntity findByZaloId(String zaloId);
    UserEntity findByFollowerZaloId(String followerId);
    UserEntity findByCustomerId(int customerId);
    @Query("select p.id from UserEntity p where p.roleId = ?1")
    List<Integer> findByRoleId(int roleId);
    List<UserEntity> findByCustomerIdIn(List<Integer> customerIds);
}
