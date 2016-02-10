package com.jschool;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by infinity on 10.02.16.
 */
public class CustomTransaction {

    private EntityTransaction entityTransaction;

    public CustomTransaction(EntityManager entityManager) {
        entityTransaction = entityManager.getTransaction();
    }

    public void begin(){
        entityTransaction.begin();
    }

    public void commit(){
        entityTransaction.commit();
    }

    public void rollbackIfActive(){
        if (entityTransaction.isActive()){
            entityTransaction.rollback();
        }
    }
}
