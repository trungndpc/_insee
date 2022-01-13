package vn.insee.jpa.repository.custom;


import vn.insee.jpa.repository.custom.metrics.SuccessPredictMetric;

import java.util.List;

public interface PredictRepositoryCustom {
    List<SuccessPredictMetric> getListSuccessPredict(int promotionId);
}
