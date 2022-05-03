package vn.insee.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.repository.PredictFootballMatchRepository;

@Service
public class PredictFootballMatchService {

    @Autowired
    private PredictFootballMatchRepository repository;

    public PredictMatchFootballFormEntity findByUserIdAndMatchId(int userId, int matchId) {
        return repository.findByUserIdAndMatchId(userId, matchId);
    }

    public PredictMatchFootballFormEntity createOrSave(PredictMatchFootballFormEntity entity) {
        return repository.saveAndFlush(entity);
    }

}
