package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.repository.PromotionRepository;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public PromotionEntity get(int id) {
        return promotionRepository.getOne(id);
    }
}
