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
        Truck element = truckDao.findUniqueByNumber(truck.getNumber());
        if (element == null)
            truckDao.create(truck);
    }

    public void update(Truck truck){
        truckDao.update(truck);
    }

    public void delete(String truckNumber){
        Truck truck = truckDao.findUniqueByNumber(truckNumber);
        if (truck.getOreder() == null)
            truckDao.delete(truck);
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
