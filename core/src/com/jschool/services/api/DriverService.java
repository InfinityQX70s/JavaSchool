package com.jschool.services.api;

import com.jschool.entities.Driver;
import com.jschool.services.api.exception.ServiceExeption;

import java.util.List;
import java.util.Map;

/**
 * Created by infinity on 11.02.16.
 */
public interface DriverService {

    void addDriver(Driver driver) throws ServiceExeption;
    void updateDrive(Driver driver) throws ServiceExeption;
    void deleteDriver(int number) throws ServiceExeption;
    Driver getDriverByPersonalNumber(int number) throws ServiceExeption;
    List<Driver> findAllDrivers() throws ServiceExeption;
    Map<Driver,Integer> findAllAvailableDrivers(int hoursWorked) throws ServiceExeption;

}
