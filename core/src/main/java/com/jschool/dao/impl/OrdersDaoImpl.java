package com.jschool.dao.impl;

import com.jschool.dao.api.OrdersDao;
import com.jschool.entities.OrdersEntity;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class OrdersDaoImpl extends GenericDaoImpl<OrdersEntity> implements OrdersDao{

    public OrdersEntity findByNumber(int number) {
        TypedQuery<OrdersEntity> query =
                entityManager.createNamedQuery("Orders.findByNumber", OrdersEntity.class);
        query.setParameter("number", number);
        return query.getSingleResult();
    }

    public List<OrdersEntity> findAllByState(boolean isDone) {
        TypedQuery<OrdersEntity> query =
                entityManager.createNamedQuery("Orders.findAllByState", OrdersEntity.class);
        query.setParameter("doneState", isDone);
        return query.getResultList();
    }

    public List<OrdersEntity> findAll() {
        TypedQuery<OrdersEntity> query =
                entityManager.createNamedQuery("Orders.findAll", OrdersEntity.class);
        return query.getResultList();
    }
}
