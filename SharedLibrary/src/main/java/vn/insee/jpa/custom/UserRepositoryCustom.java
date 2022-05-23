package vn.insee.jpa.custom;

import org.springframework.data.jpa.domain.Specification;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserCityMetric> statisticUserByCity();
    List<UserDataMetric> statisticUserByDate(List<Integer> statuses);
    List<UserEntity> findAllWithIdOnly(Specification<UserEntity> spec);
}
