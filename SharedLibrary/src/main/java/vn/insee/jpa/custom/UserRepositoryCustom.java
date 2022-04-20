package vn.insee.jpa.custom;

import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserCityMetric> statisticUserByCity();
    List<UserDataMetric> statisticUserByDate(List<Integer> statuses);
}
