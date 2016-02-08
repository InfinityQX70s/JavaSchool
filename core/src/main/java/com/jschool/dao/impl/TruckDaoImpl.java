package com.jschool.dao.impl;

import com.jschool.dao.api.TruckDao;
import com.jschool.entities.TruckEntity;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class TruckDaoImpl extends GenericDaoImpl<TruckEntity> implements TruckDao {

    public TruckEntity findByNumber(String number) {
        TypedQuery<TruckEntity> query =
                entityManager.createNamedQuery("Truck.findByNumber", TruckEntity.class);
        query.setParameter("number", number);
        return query.getSingleResult();
    }

    public List<TruckEntity> findAllFreeByStateAndCapacity(boolean isRepair, int capacity) {
        TypedQuery<TruckEntity> query =
                entityManager.createNamedQuery("Truck.findAllFreeByStateAndCapacity", TruckEntity.class);
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
