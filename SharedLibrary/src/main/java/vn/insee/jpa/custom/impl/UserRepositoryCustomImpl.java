package vn.insee.jpa.custom.impl;

import org.springframework.stereotype.Repository;
import vn.insee.common.status.StatusUser;
import vn.insee.jpa.custom.UserRepositoryCustom;
import vn.insee.jpa.entity.UserEntity;
import vn.insee.jpa.entity.UserEntity_;
import vn.insee.jpa.metric.UserCityMetric;
import vn.insee.jpa.metric.UserDataMetric;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<UserCityMetric> statisticUserByCity() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<UserEntity> root = query.from(UserEntity.class);
        query.groupBy(root.get(UserEntity_.cityId));
        query.multiselect(root.get(UserEntity_.cityId), cb.count(root.get(UserEntity_.id)));
//        query.where(cb.equal(root.get(UserEntity_.STATUS), StatusUser.APPROVED));
        List<Object[]> resultList = entityManager.createQuery(query).getResultList();
        return resultList.stream().map(r -> new UserCityMetric(Integer.parseInt(r[0].toString()),
                Integer.parseInt(r[1].toString()))).collect(Collectors.toList());
    }

    @Override
    public List<UserDataMetric> statisticUserByDate() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<UserEntity> root = query.from(UserEntity.class);
        Expression<Date> dateExpression = root.get(UserEntity_.createdTime).as(Date.class);
        query.groupBy(dateExpression);
        query.orderBy(cb.desc(dateExpression));
        query.multiselect(dateExpression, cb.count(root.get(UserEntity_.id)));
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(15);
        List<Object[]> resultList = typedQuery.getResultList();
        return resultList.stream().map(r -> new UserDataMetric(r[0].toString(), Integer.parseInt(r[1].toString())))
                .collect(Collectors.toList());
    }
}
