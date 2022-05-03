package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.promotion.PredictFootballPromotionEntity;
import vn.insee.jpa.repository.PredictFootballPromotionRepository;


@Service
public class FootballPromotionService {

    @Autowired
    private PredictFootballPromotionRepository repository;

    public PredictFootballPromotionEntity createOrUpdate(PredictFootballPromotionEntity entity) {
        return repository.saveAndFlush(entity);
    }

    public PredictFootballPromotionEntity get(int id) {
        return repository.getOne(id);
    }

}
