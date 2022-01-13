package vn.insee.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.LoyaltyEntity;

import java.util.List;

public interface LoyaltyRepository extends JpaRepository<LoyaltyEntity, Integer> {
    LoyaltyEntity findByCustomerIdAndPromotionId(int customerId, int promotionId);
    List<LoyaltyEntity> findByCustomerId(int customerId);
}
