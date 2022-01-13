package vn.insee.jpa.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.insee.jpa.entity.CustomerEntity;
import vn.insee.jpa.repository.custom.metrics.CustomerDateMetric;
import vn.insee.jpa.repository.custom.metrics.CustomerLocationMetric;
import vn.insee.jpa.repository.custom.metrics.Registered;

import java.util.List;

public interface CustomerRepositoryCustom {
    Page<CustomerEntity> getListByStatusAndLocationAndLinkedUser(Integer status, Integer location, Boolean isLinkedUser, Pageable pageable);
    List<CustomerLocationMetric> statisticTotalCustomerByLocation();
    List<CustomerDateMetric> statisticTotalCustomerByDate();
    List<CustomerDateMetric> statisticTotalCustomerByDateAndLocation(int location);
    List<Registered> findRegisteredByLocation(int location);
}
