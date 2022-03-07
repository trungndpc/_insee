package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;

import java.util.List;

public interface LightingQuizFormRepository extends JpaRepository<LightingQuizFormEntity, Integer> {
    LightingQuizFormEntity findByUserIdAndPromotionIdAndTopicId(int userId, int promotionId, String topicId);
    List<LightingQuizFormEntity> findByPromotionIdAndTopicId(int promotionId, String topicId);
    List<LightingQuizFormEntity> findByPromotionId(int promotionId);

}
