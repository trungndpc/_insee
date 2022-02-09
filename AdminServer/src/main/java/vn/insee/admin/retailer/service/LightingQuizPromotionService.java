package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.promotion.LightingQuizPromotionEntity;
import vn.insee.jpa.repository.LightingQuizPromotionRepository;

@Service
public class LightingQuizPromotionService {

    @Autowired
    private LightingQuizPromotionRepository lzQuizPromotionRepository;

    public LightingQuizPromotionEntity get(int id) {
        return lzQuizPromotionRepository.getOne(id);
    }

    public LightingQuizPromotionEntity create(LightingQuizPromotionEntity lightingQuizPromotionEntity) {
        lightingQuizPromotionEntity.setStatus(StatusPromotion.INI);
        return lzQuizPromotionRepository.saveAndFlush(lightingQuizPromotionEntity);
    }

    public LightingQuizPromotionEntity update(LightingQuizPromotionEntity promotionEntity) {
        return lzQuizPromotionRepository.saveAndFlush(promotionEntity);
    }
}
