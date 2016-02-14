package com.jschool.services.api;

import com.jschool.entities.Truck;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface TruckService {

     void addTruck(Truck truck);
     void updateTruck(Truck truck);
     void deleteTruck(String number);
     Truck getTruckByNumber(String number);
     List<Truck> findAllTrucks();
     List<Truck> findAllAvailableTrucksByMinCapacity(int capacity);
}
