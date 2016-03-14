package com.jschool.dao.impl;

import com.jschool.dao.api.DriverDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
@Repository
public class DriverDaoImpl extends GenericDaoImpl<Driver> implements DriverDao {

    private static final Logger LOG = Logger.getLogger(DriverDaoImpl.class);

    @Override
    public Driver findUniqueByNumber(int number) throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findByNumber", Driver.class);
            query.setParameter("number", number);
            List<Driver> drivers = query.getResultList();
            Driver driver = null;
            if (!drivers.isEmpty())
                driver = drivers.get(0);
            return driver;
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    /**Return all drivers from DB where first name and last name equals the same fields
     * in table Driver in DB
     * @param firstName
     * @param lastName
     * @return list of drivers which equals params fist and last name
     * @throws DaoException
     */
    @Override
    public List<Driver> findAllByFirstNameAndLastName(String firstName, String lastName) throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findAllByFirstNameAndLastName", Driver.class);
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    @Override
    public List<Driver> findAll() throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findAll", Driver.class);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    /** Return all drivers which do not have order at this time
     * @return list of driver which do not have orders
     * @throws DaoException
     */
    @Override
    public List<Driver> findAllFreeDrivers(String city) throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findAllFreeDrivers", Driver.class);
            query.setParameter("city", city);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    @Override
    public List<Driver> findAllByOffset(int offset, int limit) throws DaoException {
        try {
            TypedQuery<Driver> query =
                    entityManager.createNamedQuery("Driver.findAll", Driver.class);
            query.setFirstResult(offset)
                    .setMaxResults(limit);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
