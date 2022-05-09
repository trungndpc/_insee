package vn.insee.retailer.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.repository.PredictFootballMatchRepository;

@Service
public class PredictFootballMatchService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private PredictFootballMatchRepository repository;

    public PredictMatchFootballFormEntity findByUserIdAndMatchId(int userId, int matchId) {
        LOGGER.info("userId: " + userId + ", matchId: " + matchId);
        return repository.findByUserIdAndMatchId(userId, matchId);
    }

    public PredictMatchFootballFormEntity createOrSave(PredictMatchFootballFormEntity entity) {
        return repository.saveAndFlush(entity);
    }

}
