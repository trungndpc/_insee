package vn.insee.jpa.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.insee.jpa.entity.RetailerEntity;

public interface RetailerRepositoryCustom {
    Page<RetailerEntity> find(Integer city, Integer district, Integer cement, Pageable pageable);
}
