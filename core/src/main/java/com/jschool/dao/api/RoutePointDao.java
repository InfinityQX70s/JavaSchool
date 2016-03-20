package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Order;
import com.jschool.entities.RoutePoint;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface RoutePointDao extends GenericDao<RoutePoint>{

    List<RoutePoint> findByOrder(Order order) throws DaoException;
}
