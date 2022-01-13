package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.insee.jpa.entity.GiftEntity;

import java.util.List;

public interface GiftRepository extends JpaRepository<GiftEntity, Integer> {
    List<GiftEntity> findByCustomerId(int customerId);
    Page<GiftEntity> findAll(Specification specification, Pageable pageable);
    long countByType(int type);
    @Query("delete from GiftEntity g where g.customerId = ?1")
    void deleteByCustomerId(int customerId);
}
