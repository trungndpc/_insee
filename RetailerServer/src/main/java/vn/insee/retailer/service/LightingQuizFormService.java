package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.form.LightingQuizFormEntity;
import vn.insee.jpa.repository.LightingQuizFormRepository;

@Service
public class LightingQuizFormService {

    @Autowired
    private LightingQuizFormRepository lightingQuizFormRepository;


    public LightingQuizFormEntity submit(LightingQuizFormEntity lightingQuizFormEntity) {
        return lightingQuizFormRepository.saveAndFlush(lightingQuizFormEntity);
    }

    public LightingQuizFormEntity find(int uid, int promotionId, String topicId) {
        return lightingQuizFormRepository.findByUserIdAndPromotionIdAndTopicId(uid, promotionId, topicId);
    }


}
