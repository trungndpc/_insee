package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.GiftEntity;

public interface GiftRepository extends JpaRepository<GiftEntity, Integer> {
}