package com.jschool.dao.api;

import com.jschool.entities.Order;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface OrdersDao extends GenericDao<Order> {

    Order findUniqueByNumber(int number);
    List<Order> findAllByState(boolean isDone);
    List<Order> findAll();
}
