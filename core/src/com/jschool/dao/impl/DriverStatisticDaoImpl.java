package com.jschool.dao.impl;

import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.entities.DriverStatistic;

import javax.persistence.EntityManager;

/**
 * Created by infinity on 07.02.16.
 */
public class DriverStatisticDaoImpl extends GenericDaoImpl<DriverStatistic> implements DriverStatisticDao{
    public DriverStatisticDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
