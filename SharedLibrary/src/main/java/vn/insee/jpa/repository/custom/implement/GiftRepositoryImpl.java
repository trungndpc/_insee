package vn.insee.jpa.repository.custom.implement;

import org.springframework.stereotype.Repository;
import vn.insee.jpa.repository.custom.GiftRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GiftRepositoryImpl implements GiftRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

}
