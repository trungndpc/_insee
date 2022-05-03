package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.entity.AccumulationEntity;

public interface AccumulationRepository extends JpaRepository<AccumulationEntity, Integer>, JpaSpecificationExecutor<AccumulationEntity> {
}
