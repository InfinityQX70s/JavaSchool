package com.jschool.dao.impl;

import com.jschool.dao.api.GenericDao;
import com.jschool.dao.api.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Created by infinity on 07.02.16.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    protected EntityManager entityManager;

    public GenericDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(T entity) throws DaoException {
        try {
            entityManager.persist(entity);
        }catch (Exception e){
            throw new DaoException(e);
        }
    }

    public void update(T entity) throws DaoException {
        try {
            entityManager.merge(entity);
        }catch (Exception e){
            throw new DaoException(e);
        }
    }

    public void delete(T entity) throws DaoException {
        try {
            entityManager.remove(entity);
        }catch (Exception e){
            throw new DaoException(e);
        }
    }
}
