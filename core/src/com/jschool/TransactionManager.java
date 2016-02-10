package com.jschool;

import javax.persistence.EntityManager;

/**
 * Created by infinity on 10.02.16.
 */
public class TransactionManager {

    private CustomTransaction customTransaction;

    public TransactionManager(EntityManager entityManager) {
        customTransaction = new CustomTransaction(entityManager);
    }

    public CustomTransaction getTransaction(){
        return customTransaction;
    }
}
