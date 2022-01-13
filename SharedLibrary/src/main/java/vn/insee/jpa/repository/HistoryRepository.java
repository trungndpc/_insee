package vn.insee.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.HistoryEntity;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {
    List<HistoryEntity> findByUserId(int userId);
}
