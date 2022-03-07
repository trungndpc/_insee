package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.entity.form.StockFormEntity;

import java.util.List;

public interface StockFormRepository extends JpaRepository<StockFormEntity, Integer> , JpaSpecificationExecutor<StockFormEntity> {
    List<StockFormEntity> findByPromotionId(int promotionId);
}
