package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;

public interface PredictFootballMatchRepository extends JpaRepository<PredictMatchFootballFormEntity, Integer> {
    PredictMatchFootballFormEntity findByUserIdAndMatchId(Integer userId, Integer matchId);
}
