package com.jschool.dao.impl;

import com.jschool.dao.api.GenericDao;
import com.jschool.dao.api.exception.DaoException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by infinity on 07.02.16.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    private static final Logger LOG = Logger.getLogger(GenericDaoImpl.class);

    @PersistenceContext
    protected EntityManager entityManager;


    /**Do basic operation for all entities such as create
     * @param entity
     * @throws DaoException
     */
    @Override
    public void create(T entity) throws DaoException {
        try {
            entityManager.persist(entity);
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw new DaoException(e);
        }
    }

    /**Do basic operation for all entities such as update
     * @param entity
     * @throws DaoException
     */
    @Override
    public void update(T entity) throws DaoException {
        try {
            entityManager.merge(entity);
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw new DaoException(e);
        }
    }

    /**Do basic operation for all entities such as delete
     * @param entity
     * @throws DaoException
     */
    @Override
    public void delete(T entity) throws DaoException {
        try {
            entityManager.remove(entity);
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw new DaoException(e);
        }
    }
}
