package com.jschool.dao.impl;

import com.jschool.dao.api.OrdersDao;
import com.jschool.entities.Order;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class OrdersDaoImpl extends GenericDaoImpl<Order> implements OrdersDao{

    public OrdersDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Order findUniqueByNumber(int number) {
        TypedQuery<Order> query =
                entityManager.createNamedQuery("Orders.findByNumber", Order.class);
        query.setParameter("number", number);
        Order order = query.getSingleResult();
        entityManager.refresh(order);
        return order;
    }

    public List<Order> findAllByState(boolean isDone) {
        TypedQuery<Order> query =
                entityManager.createNamedQuery("Orders.findAllByState", Order.class);
        query.setParameter("doneState", isDone);
        return query.getResultList();
    }

    public List<Order> findAll() {
        TypedQuery<Order> query =
                entityManager.createNamedQuery("Orders.findAll", Order.class);
        return query.getResultList();
    }

}
