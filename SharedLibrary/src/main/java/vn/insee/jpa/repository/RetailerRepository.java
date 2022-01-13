package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.RetailerEntity;
import vn.insee.jpa.repository.custom.RetailerRepositoryCustom;

public interface RetailerRepository extends JpaRepository<RetailerEntity, Integer>, RetailerRepositoryCustom {
    Page<RetailerEntity> findAll(Specification specification, Pageable pageable);
}
