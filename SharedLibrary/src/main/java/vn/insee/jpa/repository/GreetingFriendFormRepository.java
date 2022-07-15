package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.entity.form.GreetingFriendFormEntity;

import java.util.List;


public interface GreetingFriendFormRepository extends JpaRepository<GreetingFriendFormEntity, Integer>, JpaSpecificationExecutor<GreetingFriendFormEntity> {
    GreetingFriendFormEntity findByUserIdAndPromotionId(int userId, int promotionId);
    List<GreetingFriendFormEntity> findByPromotionId(int promotionId);

}
