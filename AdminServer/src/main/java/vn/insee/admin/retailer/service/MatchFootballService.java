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
import vn.insee.admin.retailer.woker.Scheduler;
import vn.insee.admin.retailer.woker.task.Notify2PredictMatchFootballTask;
import vn.insee.admin.retailer.woker.task.UpdateStatusMatchTask;
import vn.insee.common.status.MatchFootballStatus;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.repository.MatchFootballRepository;
import vn.insee.jpa.specification.MatchFootballSpecification;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class MatchFootballService {

    @Autowired
    private MatchFootballRepository repository;

    @Autowired
    private MatchFootballSpecification footballSpecification;

    @Autowired
    private Scheduler scheduler;

    public Page<MatchFootballEntity> find(String season, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "id"));
        Specification<MatchFootballEntity> specs =  Specification.where(null);
        specs = specs.and(footballSpecification.isSeason(season));
        return repository.findAll(specs, pageable);
    }

    public MatchFootballEntity getById(int id) {
        return repository.getOne(id);
    }

    public MatchFootballEntity createOrUpdate(MatchFootballEntity entity) {
        return repository.saveAndFlush(entity);
    }

    @EventListener
    public void test(ContextRefreshedEvent event) {
        final int PROMOTION_ID_PREDICT_FOOTBALL = 376;
        scheduleFootball(PROMOTION_ID_PREDICT_FOOTBALL);
    }

    private void scheduleFootball(int promotionId) {
        List<MatchFootballEntity> entities =
                repository.findByStatus(MatchFootballStatus.INIT);
        if (entities != null && !entities.isEmpty()) {
            entities.forEach(entity -> {
                ZonedDateTime timeStart = entity.getTimeStart();
                ZonedDateTime zonedDateTime = timeStart.minusHours(1);
                Notify2PredictMatchFootballTask task = new Notify2PredictMatchFootballTask();
                task.setMatchId(entity.getId());
                task.setPromotionId(promotionId);
                scheduler.addNotify2PredictFootball(zonedDateTime.toLocalDateTime(), task);

                UpdateStatusMatchTask updateStatusMatchTask = new UpdateStatusMatchTask();
                updateStatusMatchTask.setMatchId(entity.getId());
                scheduler.addUpdateStatusMatch(timeStart.toLocalDateTime(), updateStatusMatchTask);
            });
        }
    }
}
