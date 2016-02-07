package com.jschool.dao.impl;

import com.jschool.dao.api.TruckDao;
import com.jschool.entities.TruckEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class TruckDaoImpl extends GenericDaoImpl<TruckEntity> implements TruckDao {

    public List<TruckEntity> findAllByStateAndCapacity(boolean isRepair, int capacity) {
        TypedQuery<TruckEntity> query =
                entityManager.createNamedQuery("Truck.findAllByStateAndCapacity", TruckEntity.class);
        query.setParameter("repairState", isRepair);
        query.setParameter("capacity", capacity);
        return query.getResultList();
    }

    public List<TruckEntity> findAll() {
        TypedQuery<TruckEntity> query =
                entityManager.createNamedQuery("Truck.findAll", TruckEntity.class);
        return query.getResultList();
    }
}
