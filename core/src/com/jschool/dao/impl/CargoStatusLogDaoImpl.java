package com.jschool.dao.impl;

import com.jschool.dao.api.CargoStatusLogDao;
import com.jschool.entities.CargoStatusLog;

import javax.persistence.EntityManager;

/**
 * Created by infinity on 07.02.16.
 */
public class CargoStatusLogDaoImpl extends GenericDaoImpl<CargoStatusLog> implements CargoStatusLogDao {

    public CargoStatusLogDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
