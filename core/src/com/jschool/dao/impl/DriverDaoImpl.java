package com.jschool.dao.impl;

import com.jschool.dao.api.DriverDao;
import com.jschool.entities.Driver;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class DriverDaoImpl extends GenericDaoImpl<Driver> implements DriverDao {

    public DriverDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Driver findUniqueByNumber(int number) {
        TypedQuery<Driver> query =
                entityManager.createNamedQuery("Driver.findByNumber", Driver.class);
        query.setParameter("number", number);
        return query.getSingleResult();
    }

    public List<Driver> findAllByFirstNameAndLastName(String firstName, String lastName) {
        TypedQuery<Driver> query =
                entityManager.createNamedQuery("Driver.findByFirstNameAndLastName", Driver.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    public List<Driver> findAll() {
        TypedQuery<Driver> query =
                entityManager.createNamedQuery("Driver.findAll", Driver.class);
        return query.getResultList();
    }
}
