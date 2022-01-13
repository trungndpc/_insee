package vn.insee.jpa.repository.custom.implement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.insee.jpa.entity.ConstructionEntity;
import vn.insee.jpa.entity.ConstructionEntity_;
import vn.insee.jpa.entity.CustomerEntity;
import vn.insee.jpa.entity.CustomerEntity_;
import vn.insee.jpa.repository.custom.ConstructionRepositoryCustom;
import vn.insee.jpa.repository.custom.metrics.ConstructionLocationMetric;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ConstructionRepositoryImpl implements ConstructionRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ConstructionLocationMetric> statisticConstructionByLocation() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<ConstructionEntity> root = query.from(ConstructionEntity.class);
        query.groupBy(root.get(ConstructionEntity_.city));
        query.multiselect(root.get(ConstructionEntity_.city), cb.count(root.get(ConstructionEntity_.id)));
        List<Object[]> resultList = entityManager.createQuery(query).getResultList();
        return resultList.stream().map(r -> new ConstructionLocationMetric(Integer.parseInt(r[0].toString()),
                Integer.parseInt(r[1].toString()))).collect(Collectors.toList());
    }

    @Override
    public List<Integer> findByPromotionId(int promotionId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        Root<ConstructionEntity> root = query.from(ConstructionEntity.class);
        query.select(root.get(ConstructionEntity_.id));
        cb.equal(root.get(ConstructionEntity_.promotionId), promotionId);
        query.equals(cb);
        List<Integer> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }

    public Page<ConstructionEntity> searchByPhoneCustomer(String phone, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ConstructionEntity> query = builder.createQuery(ConstructionEntity.class);
        Root<ConstructionEntity> from_table = query.from(ConstructionEntity.class);
        query.select(from_table);

        Subquery<Integer> sub_query = query.subquery(Integer.class);
        Root<CustomerEntity> sub_from_table = sub_query.from(CustomerEntity.class);
        sub_query.select(sub_from_table.get(CustomerEntity_.ID));
        sub_query.where(builder.like(sub_from_table.get(CustomerEntity_.phone), phone));
        Predicate predicate = from_table.get(ConstructionEntity_.customerId).in(sub_query);
        query.where(predicate);

        int total = entityManager.createQuery(query).getResultList().size();

        TypedQuery<ConstructionEntity> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(typedQuery.getResultList(), pageable, total);
    }
}
