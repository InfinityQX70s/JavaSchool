package com.jschool.dao.impl;

import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.exception.DaoException;
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

    public Driver findUniqueByNumber(int number) throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findByNumber", Driver.class);
            query.setParameter("number", number);
            entityManager.clear();
            List<Driver> drivers = query.getResultList();
            Driver driver = null;
            if (!drivers.isEmpty())
                driver = drivers.get(0);
            return driver;
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

    public List<Driver> findAllByFirstNameAndLastName(String firstName, String lastName) throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findAllByFirstNameAndLastName", Driver.class);
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

    public List<Driver> findAll() throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findAll", Driver.class);
            entityManager.clear();
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

    public List<Driver> findAllFreeDrivers() throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findAllFreeDrivers", Driver.class);
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }
}
