package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.promotion.GreetingFriendPromotionEntity;

import java.util.List;


public interface GreetingFriendPromotionRepository extends JpaRepository<GreetingFriendPromotionEntity, Integer> {
    List<GreetingFriendPromotionEntity> findByStatus(int status);

}
