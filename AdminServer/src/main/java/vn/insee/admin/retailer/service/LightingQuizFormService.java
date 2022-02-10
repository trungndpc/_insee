package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.repository.LightingQuizFormRepository;
import vn.insee.jpa.repository.LightingQuizPromotionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LightingQuizFormService {

    @Autowired
    private LightingQuizFormRepository lightingQuizFormRepository;

    public Page<LightingQuizFormEntity> findByPromotionIdAndTopicId(int promotionId, String topicId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        List<LightingQuizFormEntity> quizFormEntities = lightingQuizFormRepository.findByPromotionIdAndTopicId(promotionId, topicId);
        if (quizFormEntities != null && !quizFormEntities.isEmpty()) {
            List<LightingQuizFormEntity> list = quizFormEntities.stream().sorted((entity1, entity2) -> {
                int p = entity1.getPoint() - entity2.getPoint();
                if (p == 0) {
                    long duration1 = entity1.getTimeEnd() - entity1.getTimeStart();
                    long duration2 = entity2.getTimeEnd() - entity2.getTimeStart();
                    return (int) (duration1 - duration2);
                }
                return p;
            }).collect(Collectors.toList());
            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), list.size());
            return new PageImpl<>(list.subList(start, end), pageable, list.size());
        }
        return Page.empty();
    }

}
