package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.custom.FormRepositoryCustom;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.PostEntity;
import vn.insee.jpa.entity.form.StockFormEntity;

import java.util.List;

public interface FormRepository extends JpaRepository<FormEntity, Integer>, JpaSpecificationExecutor<FormEntity>, FormRepositoryCustom {
    List<FormEntity> findByUserId(int userId);
    Page<FormEntity> findAll(Specification spec, Pageable pageable);
    long countByPromotionIdAndStatus(int promotionId, int status);
    long countByPromotionId(int promotionId);
}
