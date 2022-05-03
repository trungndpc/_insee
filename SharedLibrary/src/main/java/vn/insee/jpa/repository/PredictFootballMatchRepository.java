package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;

import java.util.List;

public interface PredictFootballMatchRepository extends JpaRepository<PredictMatchFootballFormEntity, Integer>, JpaSpecificationExecutor<PredictMatchFootballFormEntity> {
    PredictMatchFootballFormEntity findByUserIdAndMatchId(Integer userId, Integer matchId);
    List<PredictMatchFootballFormEntity> findByMatchId(int matchId);
}
