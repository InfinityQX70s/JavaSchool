package com.jschool;

import javax.persistence.EntityManager;

/**
 * Created by infinity on 10.02.16.
 */
public class TransactionManager {

    private EntityManager entityManager;

    public TransactionManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CustomTransaction getTransaction(){
        return new CustomTransaction(entityManager);
    }
}
