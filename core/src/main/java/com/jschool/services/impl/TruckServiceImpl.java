package com.jschool.services.impl;

import com.jschool.dao.api.TruckDao;
import com.jschool.dao.impl.TruckDaoImpl;
import com.jschool.entities.TruckEntity;

import java.security.PrivateKey;
import java.util.List;

/**
 * Created by infinity on 08.02.16.
 */
public class TruckServiceImpl {

    private TruckDao truckDao;

    public TruckServiceImpl() {
        truckDao = new TruckDaoImpl();
    }

    public void create(TruckEntity truck){
        truckDao.create(truck);
    }

    public void update(TruckEntity truck){
        truckDao.update(truck);
    }

    public void delete(TruckEntity truck){
        truckDao.delete(truck);
    }

    public TruckEntity findByNumber(String number){
        return truckDao.findByNumber(number);
    }

    public List<TruckEntity> findAll(){
        return truckDao.findAll();
    }

    public List<TruckEntity> findAllFreeByStateAndCapacity(boolean isRepair, int capacity){
        return truckDao.findAllFreeByStateAndCapacity(isRepair,capacity);
    }
}
