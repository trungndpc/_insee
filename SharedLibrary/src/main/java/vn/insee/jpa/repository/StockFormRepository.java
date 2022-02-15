package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.entity.form.StockFormEntity;

public interface StockFormRepository extends JpaRepository<StockFormEntity, Integer> , JpaSpecificationExecutor<StockFormEntity> {
}
