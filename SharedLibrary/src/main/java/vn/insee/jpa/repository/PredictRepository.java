package vn.insee.jpa.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.insee.jpa.entity.PredictEntity;
import vn.insee.jpa.repository.custom.PredictRepositoryCustom;

import java.util.List;

public interface PredictRepository extends JpaRepository<PredictEntity, Integer>, PredictRepositoryCustom {
    Page<PredictEntity> findByMatchId(int matchId, Pageable pageable);
    List<PredictEntity> findByCustomerIdAndSeasonId(Integer customerId, Integer seasonId);
    PredictEntity findByMatchIdAndCustomerId(Integer customerId, Integer matchId);
    List<PredictEntity> findByMatchId(int matchId);
    @Query("select p.customerId from PredictEntity p where p.matchId = ?1")
    List<Integer> findCustomerIdByMatchId(int matchId);
    int countByMatchIdAndStatus(int matchId, int status);
    int countByMatchId(int matchId);
    List<PredictEntity> findByCustomerId(Integer customerId);
    List<PredictEntity> findByMatchIdAndStatus(int matchId, int status);
}
