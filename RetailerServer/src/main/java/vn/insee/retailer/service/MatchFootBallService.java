package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.common.status.MatchFootballStatus;
import vn.insee.jpa.entity.MatchFootballEntity;
import vn.insee.jpa.repository.MatchFootballRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class MatchFootBallService {
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("GMT+7");

    @Autowired
    private MatchFootballRepository repository;

    public MatchFootballEntity get(int id) {
        return repository.getOne(id);
    }

    public List<MatchFootballEntity> findTop2MostRecent(String season) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), DEFAULT_ZONE);
        return repository.findTop2ByTimeStartGreaterThanAndStatusAndSeasonOrderByIdAsc(zonedDateTime, MatchFootballStatus.INIT, season);
    }


}
