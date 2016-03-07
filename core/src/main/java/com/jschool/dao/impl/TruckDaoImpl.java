package com.jschool.dao.impl;

import com.jschool.dao.api.TruckDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Truck;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
@Repository
public class TruckDaoImpl extends GenericDaoImpl<Truck> implements TruckDao {

    private static final Logger LOG = Logger.getLogger(TruckDaoImpl.class);

    @Override
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
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    /** Return list of trucks which repair state "ok"(true boolean value) or
     * "broken"(false value) and capacity greater than param in method
     * @param isRepair boolean true or false (ok or broken) state
     * @param capacity truck with which capacity we want to find
     * @return
     * @throws DaoException
     */
    @Override
    public List<Truck> findAllFreeByStateAndGreaterThanCapacity(boolean isRepair, int capacity) throws DaoException {
        try {
            TypedQuery<Truck> query =
                    entityManager.createNamedQuery("Truck.findAllFreeByStateAndCapacity", Truck.class);
            query.setParameter("repairState", isRepair);
            query.setParameter("capacity", capacity);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    @Override
    public List<Truck> findAll() throws DaoException {
        try {
            entityManager.clear(); //may be bug be carefully
            TypedQuery<Truck> query =
                    entityManager.createNamedQuery("Truck.findAll", Truck.class);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }

    public List<Truck> findAllByOffset(int offset, int limit) throws DaoException {
        try {
            entityManager.clear(); //may be bug be carefully
            TypedQuery<Truck> query =
                    entityManager.createNamedQuery("Truck.findAll", Truck.class);
            query.setFirstResult(offset)
                    .setMaxResults(limit);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
