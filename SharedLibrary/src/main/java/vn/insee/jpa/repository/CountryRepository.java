package vn.insee.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.insee.jpa.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
}
