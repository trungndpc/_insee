package vn.insee.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.SubmitConstructionEntity;

import java.util.List;

public interface SubmitConstructionRepository extends JpaRepository<SubmitConstructionEntity, Integer> {
    List<SubmitConstructionEntity> findByConstructionId(int constructionId);
    List<SubmitConstructionEntity> findByConstructionIdAndStatus(int constructionId, int status);
}
