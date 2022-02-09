package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.promotion.StockPromotionEntity;

public interface StockPromotionRepository extends JpaRepository<StockPromotionEntity, Integer> {
}
