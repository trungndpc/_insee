package vn.insee.jpa.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.insee.jpa.entity.ConstructionEntity;
import vn.insee.jpa.repository.custom.metrics.ConstructionLocationMetric;

import java.util.List;

public interface ConstructionRepositoryCustom {
    List<ConstructionLocationMetric> statisticConstructionByLocation();
    List<Integer> findByPromotionId(int promotionId);
    Page<ConstructionEntity> searchByPhoneCustomer(String phone, Pageable pageable);
}
