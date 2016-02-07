package com.jschool.dao.api;

import com.jschool.entities.OrdersEntity;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface OrdersDao extends GenericDao<OrdersEntity> {

    OrdersEntity findByNumber(int number);
    List<OrdersEntity> findAllByState(boolean isDone);
    List<OrdersEntity> findAll();
}
