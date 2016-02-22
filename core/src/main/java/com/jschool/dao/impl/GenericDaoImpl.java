package com.jschool.dao.impl;

import com.jschool.dao.api.GenericDao;
import com.jschool.dao.api.exception.DaoException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;


/**
 * Created by infinity on 07.02.16.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    private static final Logger LOG = Logger.getLogger(GenericDaoImpl.class);

    protected EntityManager entityManager;

    public GenericDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(T entity) throws DaoException {
        try {
            entityManager.persist(entity);
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw new DaoException(e);
        }
    }

    public void update(T entity) throws DaoException {
        try {
            entityManager.merge(entity);
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw new DaoException(e);
        }
    }

    public void delete(T entity) throws DaoException {
        try {
            entityManager.remove(entity);
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw new DaoException(e);
        }
    }
}
