package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.BillEntity;

import java.util.List;

public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    List<BillEntity> findByLabelId(String labelId);
}
