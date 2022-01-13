package vn.insee.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.insee.jpa.entity.CustomerEntity;
import vn.insee.jpa.repository.custom.CustomerRepositoryCustom;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>, CustomerRepositoryCustom {
    CustomerEntity findByPhone(String phone);
    Page<CustomerEntity> findByStatusAndIsLinkedUser(int status, boolean isLinkedUser, Pageable pageable);
    Page<CustomerEntity> findByIsLinkedUser(boolean isLinkedUser, Pageable pageable);
    CustomerEntity findByUserId(Integer userId);
    Page<CustomerEntity> findAll(Specification specification, Pageable pageable);
    @Query("select p.id from CustomerEntity p where p.mainAreaId = ?1 and status = 2")
    List<Integer> findByCustomerIdByLocation(Integer mainAreaId);
    long countByStatus(int status);
    List<CustomerEntity> findByStatus(int status);
}
