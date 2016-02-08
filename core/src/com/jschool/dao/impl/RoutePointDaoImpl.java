package com.jschool.dao.impl;

import com.jschool.dao.api.RoutePointDao;
import com.jschool.entities.RoutePoint;

import javax.persistence.EntityManager;

/**
 * Created by infinity on 07.02.16.
 */
public class RoutePointDaoImpl extends GenericDaoImpl<RoutePoint> implements RoutePointDao{
    public RoutePointDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
