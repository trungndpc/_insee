package vn.insee.admin.retailer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.insee.jpa.entity.form.PredictMatchFootballFormEntity;
import vn.insee.jpa.repository.PredictFootballMatchRepository;
import vn.insee.jpa.specification.PredictMatchFootballFormSpecification;

import java.util.List;

@Service
public class PredictFootballFormService {

    @Autowired
    private PredictFootballMatchRepository repository;

    @Autowired
    private PredictMatchFootballFormSpecification matchFootballFormSpecification;

    public Page<PredictMatchFootballFormEntity> list(int matchId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<PredictMatchFootballFormEntity> specs =  Specification.where(null);
        specs = specs.and(matchFootballFormSpecification.isMatchId(matchId));
        return repository.findAll(specs, pageable);
    }

   public List<PredictMatchFootballFormEntity> findByMatch(int match) {
        return repository.findByMatchId(match);
   }

   public List<PredictMatchFootballFormEntity> save(List<PredictMatchFootballFormEntity> entities) {
        return repository.saveAll(entities);
   }

}