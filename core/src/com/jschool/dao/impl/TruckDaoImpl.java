package com.jschool.dao.impl;

import com.jschool.dao.api.TruckDao;
import com.jschool.entities.Truck;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class TruckDaoImpl extends GenericDaoImpl<Truck> implements TruckDao {

    public TruckDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Truck findUniqueByNumber(String number) {
        TypedQuery<Truck> query =
                entityManager.createNamedQuery("Truck.findByNumber", Truck.class);
        query.setParameter("number", number);
        return query.getSingleResult();
    }

    public List<Truck> findAllFreeByStateAndGreaterThanCapacity(boolean isRepair, int capacity) {
        TypedQuery<Truck> query =
                entityManager.createNamedQuery("Truck.findAllFreeByStateAndCapacity", Truck.class);
        query.setParameter("repairState", isRepair);
        query.setParameter("capacity", capacity);
        return query.getResultList();
    }

    public List<Truck> findAll() {
        TypedQuery<Truck> query =
                entityManager.createNamedQuery("Truck.findAll", Truck.class);
        return query.getResultList();
    }
}
