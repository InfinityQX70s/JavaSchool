package com.jschool.dao.api;

import com.jschool.entities.Truck;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface TruckDao extends GenericDao<Truck> {

    Truck findUniqueByNumber(String number);
    List<Truck> findAllFreeByStateAndGreaterThanCapacity(boolean isRepair, int capacity);
    List<Truck> findAll();
}
