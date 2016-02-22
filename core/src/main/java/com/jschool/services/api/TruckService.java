package com.jschool.services.api;

import com.jschool.entities.Truck;
import com.jschool.services.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface TruckService {

     void addTruck(Truck truck) throws ServiceException;
     void updateTruck(Truck truck) throws ServiceException;
     void deleteTruck(String number) throws ServiceException;
     Truck getTruckByNumber(String number) throws ServiceException;
     List<Truck> findAllTrucks() throws ServiceException;
     List<Truck> findAllAvailableTrucksByMinCapacity(int capacity) throws ServiceException;
}
