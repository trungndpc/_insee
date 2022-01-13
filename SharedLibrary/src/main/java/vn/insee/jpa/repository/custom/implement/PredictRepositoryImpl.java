package vn.insee.jpa.repository.custom.implement;

import org.springframework.stereotype.Repository;
import vn.insee.common.status.PredictStatus;
import vn.insee.jpa.entity.PredictEntity;
import vn.insee.jpa.entity.PredictEntity_;
import vn.insee.jpa.repository.custom.PredictRepositoryCustom;
import vn.insee.jpa.repository.custom.metrics.SuccessPredictMetric;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PredictRepositoryImpl  implements PredictRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<SuccessPredictMetric> getListSuccessPredict(int promotionId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<PredictEntity> root = query.from(PredictEntity.class);
        Predicate predicateStatus = cb.equal(root.get(PredictEntity_.status), PredictStatus.SUCCESS.getValue());
        Predicate predicateSeasonId = cb.equal(root.get(PredictEntity_.seasonId), promotionId);
        query.where(cb.and(predicateStatus, predicateSeasonId));
        query.groupBy(root.get(PredictEntity_.customerId));
        Expression<Long> count = cb.count(root.get(PredictEntity_.id));
        query.orderBy(cb.desc(count));

        query.multiselect(root.get(PredictEntity_.customerId), count);
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(200);
        List<Object[]> resultList = typedQuery.getResultList();
        return resultList.stream().map(r -> new SuccessPredictMetric(Integer.parseInt(r[0].toString()), Integer.parseInt(r[1].toString())))
                .collect(Collectors.toList());
    }
}
