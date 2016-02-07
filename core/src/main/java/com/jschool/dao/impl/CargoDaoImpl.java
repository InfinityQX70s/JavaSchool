package com.jschool.dao.impl;

import com.jschool.dao.api.CargoDao;
import com.jschool.entities.CargoEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class CargoDaoImpl extends GenericDaoImpl<CargoEntity> implements CargoDao {

    public CargoEntity findByNumber(int number) {
        TypedQuery<CargoEntity> query =
                entityManager.createNamedQuery("Cargo.findByNumber", CargoEntity.class);
        query.setParameter("number", number);
        return query.getSingleResult();
    }

    public CargoEntity findByName(String name) {
        TypedQuery<CargoEntity> query =
                entityManager.createNamedQuery("Cargo.findByName", CargoEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<CargoEntity> findAll() {
        TypedQuery<CargoEntity> query =
                entityManager.createNamedQuery("Cargo.findAll", CargoEntity.class);
        return query.getResultList();
    }
}
