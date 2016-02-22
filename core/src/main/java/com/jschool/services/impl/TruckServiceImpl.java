package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.TruckDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Truck;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class TruckServiceImpl implements TruckService{

    private static final Logger LOG = Logger.getLogger(TruckServiceImpl.class);

    private TruckDao truckDao;
    private TransactionManager transactionManager;

    public TruckServiceImpl(TruckDao truckDao, TransactionManager transactionManager) {
        this.truckDao = truckDao;
        this.transactionManager = transactionManager;
    }

    public void addTruck(Truck truck) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            if (element == null)
                truckDao.create(truck);
            else
                throw new ServiceException("Truck with such identifier exist", ServiceStatusCode.ALREADY_EXIST);
            ct.commit();
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void updateTruck(Truck truck) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            if (element != null && element.getOreder() == null) {
                element.setNumber(truck.getNumber());
                element.setCapacity(truck.getCapacity());
                element.setShiftSize(truck.getShiftSize());
                element.setRepairState(truck.isRepairState());
                truckDao.update(element);
                ct.commit();
            }
            if (element == null)
                throw new ServiceException("Truck not found", ServiceStatusCode.NOT_FOUND);
            if (element.getOreder() != null)
                throw new ServiceException("Truck has an order", ServiceStatusCode.ASSIGNED_ORDER);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void deleteTruck(String truckNumber) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (truck != null && truck.getOreder() == null){
                truckDao.delete(truck);
                ct.commit();
            }
            if (truck == null)
                throw new ServiceException("Truck not found", ServiceStatusCode.NOT_FOUND);
            if (truck.getOreder() != null)
                throw new ServiceException("Truck has an order", ServiceStatusCode.ASSIGNED_ORDER);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public Truck getTruckByNumber(String number) throws ServiceException {
        try {
            Truck truck = truckDao.findUniqueByNumber(number);
            if (truck == null)
                throw new ServiceException("Truck not found", ServiceStatusCode.NOT_FOUND);
            return truck;
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Truck> findAllTrucks() throws ServiceException {
        try {
            return truckDao.findAll();
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Truck> findAllAvailableTrucksByMinCapacity(int capacity) throws ServiceException {
        try {
            return truckDao.findAllFreeByStateAndGreaterThanCapacity(true,capacity);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
