package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.entity.MatchFootballEntity;

import java.time.ZonedDateTime;
import java.util.List;

public interface MatchFootballRepository extends JpaRepository<MatchFootballEntity, Integer>, JpaSpecificationExecutor<MatchFootballEntity> {
    List<MatchFootballEntity> findTop3ByTimeStartGreaterThanAndStatusAndSeason(ZonedDateTime zonedDateTime, int status, String season);
    List<MatchFootballEntity> findByStatus(int status);
}
