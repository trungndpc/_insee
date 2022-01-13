package vn.insee.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.LabelEntity;

import java.util.List;

public interface LabelRepository extends JpaRepository<LabelEntity, Integer> {
    List<LabelEntity> findByType(int type);
}
