package com.jschool.dao.impl;

import com.jschool.dao.api.DriverStatusLogDao;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatistic;
import com.jschool.entities.DriverStatusLog;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by infinity on 07.02.16.
 */
public class DriverStatusLogDaoImpl extends GenericDaoImpl<DriverStatusLog> implements DriverStatusLogDao{

    public DriverStatusLogDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public DriverStatusLog findLastStatus(Driver driver) {
        TypedQuery<DriverStatusLog> query =
                entityManager.createNamedQuery("DriverStatusLog.findLastStatus", DriverStatusLog.class);
        query.setParameter("driver", driver);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
