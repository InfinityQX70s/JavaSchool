package com.jschool.services.impl;

import com.jschool.dao.api.TruckDao;
import com.jschool.dao.impl.TruckDaoImpl;
import com.jschool.entities.Truck;

import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class TruckServiceImpl {

    private TruckDao truckDao;

    public TruckServiceImpl(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    public void create(Truck truck){
        truckDao.create(truck);
    }

    public void update(Truck truck){
        truckDao.update(truck);
    }

    public void delete(Truck truck){
        truckDao.delete(truck);
    }

    public Truck findByNumber(String number){
        return truckDao.findUniqueByNumber(number);
    }

    public List<Truck> findAll(){
        return truckDao.findAll();
    }

    public List<Truck> findAllFreeByStateAndCapacity(boolean isRepair, int capacity){
        return truckDao.findAllFreeByStateAndCapacity(isRepair,capacity);
    }
}
