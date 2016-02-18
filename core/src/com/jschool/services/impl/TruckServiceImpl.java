package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.TruckDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.dao.impl.TruckDaoImpl;
import com.jschool.entities.Truck;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceExeption;

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
            truckDao.create(truck);
            ct.commit();
        }catch (DaoException e) {
            throw new ServiceExeption(e);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void updateTruck(Truck truck) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            element.setNumber(truck.getNumber());
            element.setCapacity(truck.getCapacity());
            element.setShiftSize(truck.getShiftSize());
            element.setRepairState(truck.isRepairState());
            truckDao.update(element);
            ct.commit();
        }catch (DaoException e) {
            throw new ServiceExeption(e);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void deleteTruck(String truckNumber) throws ServiceExeption {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (truck.getOreder() == null)
                truckDao.delete(truck);
            ct.commit();
        }catch (DaoException e) {
            throw new ServiceExeption(e);
        }finally {
            ct.rollbackIfActive();
        }
    }

    public Truck getTruckByNumber(String number) throws ServiceExeption {
        try {
            return truckDao.findUniqueByNumber(number);
        }catch (DaoException e) {
            throw new ServiceExeption(e);
        }
    }

    public List<Truck> findAllTrucks() throws ServiceExeption {
        try {
            return truckDao.findAll();
        }catch (DaoException e) {
            throw new ServiceExeption(e);
        }
    }

    public List<Truck> findAllAvailableTrucksByMinCapacity(int capacity) throws ServiceExeption {
        try {
            return truckDao.findAllFreeByStateAndGreaterThanCapacity(true,capacity);
        }catch (DaoException e) {
            throw new ServiceExeption(e);
        }
    }
}
