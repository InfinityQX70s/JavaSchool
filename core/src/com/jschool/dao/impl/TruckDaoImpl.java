package com.jschool.dao.impl;

import com.jschool.dao.api.TruckDao;
import com.jschool.dao.api.exception.DaoException;
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

    public Truck findUniqueByNumber(String number) throws DaoException {
        try {
            TypedQuery<Truck> query =
                    entityManager.createNamedQuery("Truck.findByNumber", Truck.class);
            query.setParameter("number", number);
            List<Truck> trucks = query.getResultList();
            Truck truck = null;
            if (!trucks.isEmpty())
                truck = trucks.get(0);
            return truck;
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

    public List<Truck> findAllFreeByStateAndGreaterThanCapacity(boolean isRepair, int capacity) throws DaoException {
        try {
            TypedQuery<Truck> query =
                    entityManager.createNamedQuery("Truck.findAllFreeByStateAndCapacity", Truck.class);
            query.setParameter("repairState", isRepair);
            query.setParameter("capacity", capacity);
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }

    public List<Truck> findAll() throws DaoException {
        try {
            TypedQuery<Truck> query =
                    entityManager.createNamedQuery("Truck.findAll", Truck.class);
            return query.getResultList();
        }catch (Exception e){
            throw  new DaoException(e);
        }
    }
}
