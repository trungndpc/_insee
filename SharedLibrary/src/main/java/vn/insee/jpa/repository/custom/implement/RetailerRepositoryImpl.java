package vn.insee.jpa.repository.custom.implement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.insee.jpa.entity.RetailerEntity;
import vn.insee.jpa.repository.custom.RetailerRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RetailerRepositoryImpl implements RetailerRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<RetailerEntity> find(Integer city, Integer district, Integer cement, Pageable pageable) {
        String query = "SELECT * FROM promotion.retailer r";
        String where = null;
        if (city != null) {
            if (where == null) {
                where = "WHERE";
            }
            where = where + " r.city = :city";
        }

        if (district != null) {
            if (where == null) {
                where = "WHERE";
            }else {
                where = where + " AND";
            }
            where = where + " r.district = :district";
        }

        if (cement != null) {
            if (where == null) {
                where = "WHERE";
            }else {
                where = where + " AND";
            }
            where = where + " :products = ANY(r.products)";
        }

        if (where != null) {
            query = query + " " + where;
        }
        Query nativeQuery = entityManager.createNativeQuery(query, RetailerEntity.class);
        if (city != null) {
            nativeQuery.setParameter("city", city);
        }
        if (district != null) {
            nativeQuery.setParameter("district", district);
        }
        if (cement != null) {
            nativeQuery.setParameter("products", cement);
        }
        nativeQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        nativeQuery.setMaxResults(pageable.getPageSize());
        List<RetailerEntity> resultList = nativeQuery.getResultList();

        String count = "SELECT COUNT(r.id) FROM promotion.retailer r " + where;
        int total = entityManager.createNativeQuery(count).getFirstResult();
        return new PageImpl<>(nativeQuery.getResultList(), pageable, total);
    }

}
