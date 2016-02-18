package com.jschool.services.api;

import com.jschool.entities.Truck;
import com.jschool.services.api.exception.ServiceExeption;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface TruckService {

     void addTruck(Truck truck) throws ServiceExeption;
     void updateTruck(Truck truck) throws ServiceExeption;
     void deleteTruck(String number) throws ServiceExeption;
     Truck getTruckByNumber(String number) throws ServiceExeption;
     List<Truck> findAllTrucks() throws ServiceExeption;
     List<Truck> findAllAvailableTrucksByMinCapacity(int capacity) throws ServiceExeption;
}
