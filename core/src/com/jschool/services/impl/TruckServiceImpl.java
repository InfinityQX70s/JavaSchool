package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.TruckDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.dao.impl.TruckDaoImpl;
import com.jschool.entities.Truck;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceExeption;
import com.jschool.services.api.exception.StatusCode;

import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class TruckServiceImpl implements TruckService{

    private TruckDao truckDao;
    private TransactionManager transactionManager;

    public TruckServiceImpl(TruckDao truckDao, TransactionManager transactionManager) {
        this.truckDao = truckDao;
        this.transactionManager = transactionManager;
    }

    public void addTruck(Truck truck) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            if (element == null)
                truckDao.create(truck);
            else
                throw new ServiceExeption("Truck with such identifier exist", StatusCode.ALREADY_EXIST);
            ct.commit();
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void updateTruck(Truck truck) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            if (element != null) {
                element.setNumber(truck.getNumber());
                element.setCapacity(truck.getCapacity());
                element.setShiftSize(truck.getShiftSize());
                element.setRepairState(truck.isRepairState());
                truckDao.update(element);
                ct.commit();
            }else
                throw new ServiceExeption("Truck not found", StatusCode.NOT_FOUND);
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void deleteTruck(String truckNumber) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (truck != null && truck.getOreder() == null){
                truckDao.delete(truck);
                ct.commit();
            }
            if (truck == null)
                throw new ServiceExeption("Truck not found", StatusCode.NOT_FOUND);
            if (truck.getOreder() != null)
                throw new ServiceExeption("Truck has an order", StatusCode.ASSIGNED_ORDER);
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public Truck getTruckByNumber(String number) throws ServiceExeption {
        try {
            Truck truck = truckDao.findUniqueByNumber(number);
            if (truck == null)
                throw new ServiceExeption("Truck not found", StatusCode.NOT_FOUND);
            return truck;
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }

    public List<Truck> findAllTrucks() throws ServiceExeption {
        try {
            return truckDao.findAll();
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }

    public List<Truck> findAllAvailableTrucksByMinCapacity(int capacity) throws ServiceExeption {
        try {
            return truckDao.findAllFreeByStateAndGreaterThanCapacity(true,capacity);
        }catch (DaoException e) {
            throw new ServiceExeption("Unknown exception", e, StatusCode.UNKNOWN);
        }
    }
}
