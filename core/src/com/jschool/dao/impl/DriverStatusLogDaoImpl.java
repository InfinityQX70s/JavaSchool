package com.jschool.dao.impl;

import com.jschool.dao.api.DriverStatusLogDao;
import com.jschool.entities.DriverStatusLog;

import javax.persistence.EntityManager;

/**
 * Created by infinity on 07.02.16.
 */
public class DriverStatusLogDaoImpl extends GenericDaoImpl<DriverStatusLog> implements DriverStatusLogDao{

    public DriverStatusLogDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
