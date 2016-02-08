package com.jschool.dao.impl;

import com.jschool.dao.api.CargoDao;
import com.jschool.entities.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class CargoDaoImpl extends GenericDaoImpl<Cargo> implements CargoDao {

    public CargoDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Cargo findUniqueByNumber(int number) {
        TypedQuery<Cargo> query =
                entityManager.createNamedQuery("Cargo.findByNumber", Cargo.class);
        query.setParameter("number", number);
        return query.getSingleResult();
    }

    public List<Cargo> findAllByName(String name) {
        TypedQuery<Cargo> query =
                entityManager.createNamedQuery("Cargo.findByName", Cargo.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public List<Cargo> findAll() {
        TypedQuery<Cargo> query =
                entityManager.createNamedQuery("Cargo.findAll", Cargo.class);
        return query.getResultList();
    }

}
