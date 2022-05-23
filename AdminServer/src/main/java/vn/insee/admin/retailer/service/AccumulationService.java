package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.admin.retailer.util.City;
import vn.insee.admin.retailer.woker.Scheduler;
import vn.insee.admin.retailer.woker.task.ReportGroupStageSeagameTask;
import vn.insee.common.type.TypeAccumulation;
import vn.insee.jpa.entity.AccumulationEntity;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.repository.AccumulationRepository;
import vn.insee.jpa.specification.AccumulationSpecification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccumulationService {

    @Autowired
    private AccumulationRepository repository;

    @Autowired
    private AccumulationSpecification specification;

    @Autowired
    private Scheduler scheduler;

    public AccumulationEntity createOrUpdate(AccumulationEntity entity) {
        return repository.saveAndFlush(entity);
    }

    public AccumulationEntity findByUidAndPromotionAndType(int uid, int promotionId, int type) {
        Specification<AccumulationEntity> specs =  Specification.where(null);
        specs = specs.and(specification.isType(TypeAccumulation.PREDICT_FOOTBALL_COLLECT_POINT_TYPE_ONE));
        specs = specs.and(specification.isUserId(uid));
        specs = specs.and(specification.isPromotion(promotionId));
        Optional<AccumulationEntity> optional = repository.findOne(specs);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public Page<AccumulationEntity> findByPromotionAndType(int promotionId, int type, int page, int pageSize) {
        Specification<AccumulationEntity> specs =  Specification.where(null);
        specs = specs.and(specification.isType(type));
        specs = specs.and(specification.isPromotion(promotionId));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "point"));
        return repository.findAll(specs, pageable);
    }

    public List<AccumulationEntity> findByPromotionId(int promotionId) {
        return repository.findByPromotionId(promotionId);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    @EventListener
    public void schedule2Report(ContextRefreshedEvent event) {
        LocalDate localDate = LocalDate.of(2022, 05, 24);
        LocalTime localTime = LocalTime.of(9,  00);
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime timeStamp = ZonedDateTime.of( localDate, localTime, zoneId);
        scheduler.addReportStageSeagame(timeStamp.toLocalDateTime(), new ReportGroupStageSeagameTask());
    }
    @Autowired
    private UserService userService;
}
