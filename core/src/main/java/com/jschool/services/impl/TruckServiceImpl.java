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

    /**Add truck if it is not exist in db
     * @param truck entity with filling fields
     * @throws ServiceException status code TRUCK_ALREADY_EXIST
     */
    @Override
    public void addTruck(Truck truck) throws ServiceException {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            if (element == null)
                truckDao.create(truck);
            else
                throw new ServiceException("Truck with such identifier exist", ServiceStatusCode.TRUCK_ALREADY_EXIST);
            ct.commit();
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    /**Update truck if it exist and do not have order
     * @param truck entity
     * @throws ServiceException status codes TRUCK_NOT_FOUND, TRUCK_ASSIGNED_ORDER
     */
    @Override
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
                throw new ServiceException("Truck not found", ServiceStatusCode.TRUCK_NOT_FOUND);
            if (element.getOreder() != null)
                throw new ServiceException("Truck has an order", ServiceStatusCode.TRUCK_ASSIGNED_ORDER);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    /**Delete truck with curent number and if it did not has order
     * @param truckNumber
     * @throws ServiceException
     */
    @Override
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
                throw new ServiceException("Truck not found", ServiceStatusCode.TRUCK_NOT_FOUND);
            if (truck.getOreder() != null)
                throw new ServiceException("Truck has an order", ServiceStatusCode.TRUCK_ASSIGNED_ORDER);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    @Override
    public Truck getTruckByNumber(String number) throws ServiceException {
        try {
            Truck truck = truckDao.findUniqueByNumber(number);
            if (truck == null)
                throw new ServiceException("Truck not found", ServiceStatusCode.TRUCK_NOT_FOUND);
            return truck;
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    public List<Truck> findAllTrucks() throws ServiceException {
        try {
            return truckDao.findAll();
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**Return list of trucks with "ok" state and with capacity more or equals then needed
     * @param capacity
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Truck> findAllAvailableTrucksByMinCapacity(int capacity) throws ServiceException {
        try {
            return truckDao.findAllFreeByStateAndGreaterThanCapacity(true,capacity);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
