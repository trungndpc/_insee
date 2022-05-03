package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.common.type.TypeAccumulation;
import vn.insee.jpa.entity.AccumulationEntity;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.repository.AccumulationRepository;
import vn.insee.jpa.specification.AccumulationSpecification;

import java.util.Optional;

@Service
public class AccumulationService {

    @Autowired
    private AccumulationRepository repository;

    @Autowired
    private AccumulationSpecification specification;

    public AccumulationEntity createOrUpdate(AccumulationEntity entity) {
        return repository.saveAndFlush(entity);
    }

    public AccumulationEntity findByUidAndPromotionAndType(int uid, int promotionId, int type) {
        Specification<AccumulationEntity> specs =  Specification.where(null);
        specs = specs.and(specification.isType(TypeAccumulation.PREDICT_FOOTBALL_COLLECT_POINT_TYPE_ONE));
        specs = specs.and(specification.isUserId(uid));
        specs = specs.and(specification.inPromotion(promotionId));
        Optional<AccumulationEntity> optional = repository.findOne(specs);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
