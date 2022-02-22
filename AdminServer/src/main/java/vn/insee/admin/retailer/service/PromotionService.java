package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.common.status.StatusPromotion;
import vn.insee.jpa.entity.PromotionEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.PromotionRepository;
import vn.insee.jpa.specification.PromotionSpecification;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionSpecification promotionSpecification;

    public Page<PromotionEntity> find(List<Integer> types, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<PromotionEntity> specs =  Specification.where(null);
        specs = specs.and(promotionSpecification.isNotStatus(StatusPromotion.DELETED));
        if (types != null) {
            specs = specs.and(promotionSpecification.inTypes(types));
        }
        return promotionRepository.findAll(specs, pageable);
    }

    public List<PromotionEntity> find(int type, int status) {
        return promotionRepository.findByTypeAndStatus(type, status);
    }

    public List<PromotionEntity> findPromotionForMapPost() {
        return promotionRepository.findAll();
    }

    public PromotionEntity get(int id) {
        return promotionRepository.getOne(id);
    }

    public PromotionEntity updateStatus(int id, int status) {
        PromotionEntity entity = promotionRepository.getOne(id);
        entity.setStatus(status);
        return promotionRepository.saveAndFlush(entity);
    }

    public PromotionEntity update(PromotionEntity promotionEntity) {
        return promotionRepository.saveAndFlush(promotionEntity);
    }

}
