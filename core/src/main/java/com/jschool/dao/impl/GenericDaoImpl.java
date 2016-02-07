package com.jschool.dao.impl;

import com.jschool.dao.api.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by infinity on 07.02.16.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    protected EntityManager entityManager;

    public GenericDaoImpl() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("logiweb");
        entityManager = entityManagerFactory.createEntityManager( );
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
