package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.insee.jpa.custom.FormRepositoryCustom;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.PromotionEntity;

import java.util.List;

public interface FormRepository extends JpaRepository<FormEntity, Integer>, JpaSpecificationExecutor<FormEntity>, FormRepositoryCustom {
    List<FormEntity> findByUserId(int userId);

    List<FormEntity> findByPromotionId(int promotionId);

    @Query("SELECT DISTINCT userId FROM FormEntity")
    List<Integer> findDistinctUid();

    List<FormEntity> findByPromotionIdAndUserId(int promotionId, int userId);
}
