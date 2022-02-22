package vn.insee.jpa.custom;

import vn.insee.jpa.metric.*;

import java.util.List;

public interface FormRepositoryCustom {
    List<FormDateMetric> statisticFormByDate(List<Integer> promotions);
    List<FormCityMetric> statisticFormByCity(List<Integer> promotions);
    List<FormPromotionMetric> statisticFormByPromotion();

}
