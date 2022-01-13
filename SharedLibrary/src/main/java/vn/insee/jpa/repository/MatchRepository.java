package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.MatchEntity;

import java.time.ZonedDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {
    List<MatchEntity> findTop3ByTimeStartGreaterThanAndStatusAndSeasonId(ZonedDateTime startTime, int status, int season);
    Page<MatchEntity> findAll(Specification specification, Pageable pageable);
    List<MatchEntity> findAll(Specification specification);
    List<MatchEntity> findAll(Specification specification, Sort sort);

}
