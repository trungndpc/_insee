package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;

import java.util.List;

public interface LightingQuizPromotionRepository extends JpaRepository<LightingQuizPromotionEntity, Integer> {

}
