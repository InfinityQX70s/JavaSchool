package com.jschool.services.impl;

import com.jschool.CustomTransaction;
import com.jschool.TransactionManager;
import com.jschool.dao.api.TruckDao;
import com.jschool.dao.impl.TruckDaoImpl;
import com.jschool.entities.Truck;
import com.jschool.services.api.TruckService;

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

    public void addTruck(Truck truck) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            truckDao.create(truck);
            ct.commit();
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void updateTruck(Truck truck) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            truck.setId(element.getId());
            truckDao.update(truck);
            ct.commit();
        }finally {
            ct.rollbackIfActive();
        }
    }

    public void deleteTruck(String truckNumber) {
        CustomTransaction ct = transactionManager.getTransaction();
        ct.begin();
        try {
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (truck.getOreder() == null)
                truckDao.delete(truck);
            ct.commit();
        }finally {
            ct.rollbackIfActive();
        }
    }

    public Truck getTruckByNumber(String number) {
        return truckDao.findUniqueByNumber(number);
    }

    public List<Truck> findAllTrucks() {
        return truckDao.findAll();
    }

    public List<Truck> findAllAvailableTrucksByMinCapacity(int capacity) {
        return truckDao.findAllFreeByStateAndGreaterThanCapacity(true,capacity);
    }
}
