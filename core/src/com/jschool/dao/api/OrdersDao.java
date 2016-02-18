package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Order;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface OrdersDao extends GenericDao<Order> {

    Order findUniqueByNumber(int number) throws DaoException;
    List<Order> findAllByState(boolean isDone) throws DaoException;
    List<Order> findAll() throws DaoException;
}
