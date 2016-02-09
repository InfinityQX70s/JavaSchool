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

    public GenericDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(T entity) {
        entityManager.getTransaction( ).begin( );
        entityManager.persist(entity);
        entityManager.getTransaction( ).commit( );
    }

    public void update(T entity) {
        entityManager.getTransaction( ).begin( );
        entityManager.merge(entity);
        entityManager.getTransaction( ).commit( );
    }

    public void delete(T entity) {
        entityManager.getTransaction( ).begin( );
        entityManager.remove(entity);
        entityManager.getTransaction( ).commit( );
    }
}
