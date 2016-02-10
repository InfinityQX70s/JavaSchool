package com.jschool.services.impl;

import com.jschool.TransactionManager;
import com.jschool.dao.api.TruckDao;
import com.jschool.dao.impl.TruckDaoImpl;
import com.jschool.entities.Truck;

import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class TruckServiceImpl {

    private TruckDao truckDao;
    private TransactionManager transactionManager;

    public TruckServiceImpl(TruckDao truckDao, TransactionManager transactionManager) {
        this.truckDao = truckDao;
        this.transactionManager = transactionManager;
    }

    public void create(Truck truck){
        try {
            transactionManager.getTransaction().begin();
            truckDao.create(truck);
            transactionManager.getTransaction().commit();
        }finally {
            transactionManager.getTransaction().rollbackIfActive();
        }

    }

    public void update(Truck truck){
        try {
            transactionManager.getTransaction().begin();
            Truck element = truckDao.findUniqueByNumber(truck.getNumber());
            if (element.getOreder() == null) {
                truck.setId(element.getId());
                truckDao.update(truck);
            }
            transactionManager.getTransaction().commit();
        }finally {
            transactionManager.getTransaction().rollbackIfActive();
        }
    }

    public void delete(String truckNumber){
        try {
            transactionManager.getTransaction().begin();
            Truck truck = truckDao.findUniqueByNumber(truckNumber);
            if (truck.getOreder() == null)
                truckDao.delete(truck);
            transactionManager.getTransaction().commit();
        }finally {
            transactionManager.getTransaction().rollbackIfActive();
        }

    }

    public Truck findByNumber(String number){
        return truckDao.findUniqueByNumber(number);
    }

    public List<Truck> findAll(){
        return truckDao.findAll();
    }

    public List<Truck> findAllFreeByStateAndGreaterThanCapacity(boolean isRepair, int capacity){
        return truckDao.findAllFreeByStateAndGreaterThanCapacity(isRepair,capacity);
    }
}
