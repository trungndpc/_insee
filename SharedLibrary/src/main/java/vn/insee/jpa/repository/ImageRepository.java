package vn.insee.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}
