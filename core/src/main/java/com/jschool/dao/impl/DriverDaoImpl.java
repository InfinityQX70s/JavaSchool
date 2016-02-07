package com.jschool.dao.impl;

import com.jschool.dao.api.DriverDao;
import com.jschool.entities.DriverEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class DriverDaoImpl extends GenericDaoImpl<DriverEntity> implements DriverDao {

    public DriverEntity findByNumber(int number) {
        TypedQuery<DriverEntity> query =
                entityManager.createNamedQuery("Driver.findByNumber", DriverEntity.class);
        query.setParameter("number", number);
        return query.getSingleResult();
    }

    public List<DriverEntity> findByFirstNameAndLastName(String firstName, String lastName) {
        TypedQuery<DriverEntity> query =
                entityManager.createNamedQuery("Driver.findByFirstNameAndLastName", DriverEntity.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    public List<DriverEntity> findAll() {
        TypedQuery<DriverEntity> query =
                entityManager.createNamedQuery("Driver.findAll", DriverEntity.class);
        return query.getResultList();
    }
}
