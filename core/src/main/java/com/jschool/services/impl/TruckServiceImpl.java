package com.jschool.services.impl;

import com.jschool.dao.api.TruckDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Truck;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceException;
import com.jschool.services.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
@Service
public class TruckServiceImpl implements TruckService{

    private static final Logger LOG = Logger.getLogger(TruckServiceImpl.class);

    private TruckDao truckDao;

    @Autowired
    public TruckServiceImpl(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    /**Add truck if it is not exist in db
     * @param truck entity with filling fields
     * @throws ServiceException status code TRUCK_ALREADY_EXIST
     */
    @Override
    @Transactional(rollbackFor=ServiceException.class)
    public void addTruck(Truck truck) throws ServiceException {
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            if (element == null)
                truckDao.create(truck);
            else
                throw new ServiceException("Truck with such identifier exist", ServiceStatusCode.TRUCK_ALREADY_EXIST);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**Update truck if it exist and do not have order
     * @param truck entity
     * @throws ServiceException status codes TRUCK_NOT_FOUND, TRUCK_ASSIGNED_ORDER
     */
    @Override
    @Transactional(rollbackFor=ServiceException.class)
    public void updateTruck(Truck truck) throws ServiceException {
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            if (element != null && element.getOreder() == null) {
                element.setNumber(truck.getNumber());
                element.setCapacity(truck.getCapacity());
                element.setShiftSize(truck.getShiftSize());
                element.setRepairState(truck.isRepairState());
                truckDao.update(element);
            }
            if (element == null)
                throw new ServiceException("Truck not found", ServiceStatusCode.TRUCK_NOT_FOUND);
            if (element.getOreder() != null)
                throw new ServiceException("Truck has an order", ServiceStatusCode.TRUCK_ASSIGNED_ORDER);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**Delete truck with curent number and if it did not has order
     * @param truckNumber
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor=ServiceException.class)
    public void deleteTruck(String truckNumber) throws ServiceException {
        try {
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (truck != null && truck.getOreder() == null){
                truckDao.delete(truck);
            }
            if (truck == null)
                throw new ServiceException("Truck not found", ServiceStatusCode.TRUCK_NOT_FOUND);
            if (truck.getOreder() != null)
                throw new ServiceException("Truck has an order", ServiceStatusCode.TRUCK_ASSIGNED_ORDER);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor=ServiceException.class)
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
    @Transactional(rollbackFor=ServiceException.class)
    public List<Truck> findAllTrucks() throws ServiceException {
        try {
            return truckDao.findAll();
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    @Transactional(rollbackFor=ServiceException.class)
    public List<Truck> findAllTrucksByOffset(int offset, int limit) throws ServiceException {
        try {
            return truckDao.findAllByOffset(offset,limit);
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
    @Transactional(rollbackFor=ServiceException.class)
    public List<Truck> findAllAvailableTrucksByMinCapacity(int capacity) throws ServiceException {
        try {
            return truckDao.findAllFreeByStateAndGreaterThanCapacity(true,capacity);
        }catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
