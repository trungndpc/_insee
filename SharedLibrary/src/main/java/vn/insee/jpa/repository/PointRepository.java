package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.PointEntity;


public interface PointRepository extends JpaRepository<PointEntity, Integer> {
}
