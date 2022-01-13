package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.ConstructionEntity;
import vn.insee.jpa.repository.custom.ConstructionRepositoryCustom;

import java.util.List;

public interface ConstructionRepository extends JpaRepository<ConstructionEntity, Integer>, ConstructionRepositoryCustom {
    List<ConstructionEntity> findByCustomerIdAndPromotionId(int customerId, int promotionId);
    List<ConstructionEntity> findByCustomerId(int customerId);
    Page<ConstructionEntity> findByPromotionId(int promotionId, Pageable pageable);
    Page<ConstructionEntity> findByTypeAndStatus(int type, int status, Pageable pageable);
    Page<ConstructionEntity> findByType(int type, Pageable pageable);
    long countByPromotionId(int promotionId);
    long countByStatus(int status);
    Page<ConstructionEntity> findAll(Specification specification, Pageable pageable);

}
