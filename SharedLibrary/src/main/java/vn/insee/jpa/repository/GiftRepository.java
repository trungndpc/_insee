package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.insee.jpa.entity.FormEntity;
import vn.insee.jpa.entity.GiftEntity;

public interface GiftRepository extends JpaRepository<GiftEntity, Integer> , JpaSpecificationExecutor<GiftEntity> {
    long countByStatus(int status);
}
