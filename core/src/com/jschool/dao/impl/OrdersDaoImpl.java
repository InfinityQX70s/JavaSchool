package com.jschool.dao.impl;

import com.jschool.dao.api.OrdersDao;
import com.jschool.dao.api.exception.DaoException;
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

    public Order findUniqueByNumber(int number) throws DaoException {
        try {
            TypedQuery<Order> query =
                    entityManager.createNamedQuery("Orders.findByNumber", Order.class);
            query.setParameter("number", number);
            entityManager.clear();
            List<Order> orders = query.getResultList();
            Order order = null;
            if (!orders.isEmpty()){
                order = orders.get(0);
            }
            return order;
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

    public List<Order> findAllByState(boolean isDone) throws DaoException {
        try {
            TypedQuery<Order> query =
                    entityManager.createNamedQuery("Orders.findAllByState", Order.class);
            query.setParameter("doneState", isDone);
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

    public List<Order> findAll() throws DaoException {
        try {
            TypedQuery<Order> query =
                    entityManager.createNamedQuery("Orders.findAll", Order.class);
            entityManager.clear();
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

}
