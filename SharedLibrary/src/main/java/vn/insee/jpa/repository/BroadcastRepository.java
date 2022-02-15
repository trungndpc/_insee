package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.BroadcastEntity;

public interface BroadcastRepository extends JpaRepository<BroadcastEntity, Integer> {
}
