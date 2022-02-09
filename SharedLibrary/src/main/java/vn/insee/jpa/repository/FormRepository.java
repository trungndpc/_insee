package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.PostEntity;

import java.util.List;

public interface FormRepository extends JpaRepository<FormEntity, Integer> {
    List<FormEntity> findByUserId(int userId);
    Page<FormEntity> findAll(Specification spec, Pageable pageable);
    Page<FormEntity> findByPromotionId(int promotionId, Pageable pageable);
    long countByPromotionIdAndStatus(int promotionId, int status);
    long countByPromotionId(int promotionId);
}
