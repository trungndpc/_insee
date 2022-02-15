package vn.insee.jpa.custom;

import vn.insee.jpa.metric.FormCityMetric;
import vn.insee.jpa.metric.FormDateMetric;
import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;

import java.util.List;

public interface FormRepositoryCustom {
    List<FormDateMetric> statisticFormByDate(List<Integer> promotions);
    List<FormCityMetric> statisticFormByCity(List<Integer> promotions);

}
