package com.jschool.services.api;

import com.jschool.entities.Driver;
import com.jschool.services.api.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by infinity on 11.02.16.
 */
public interface DriverService {

    void addDriver(Driver driver) throws ServiceException;
    void updateDrive(Driver driver) throws ServiceException;
    void deleteDriver(int number) throws ServiceException;
    Driver getDriverByPersonalNumber(int number) throws ServiceException;
    List<Driver> findAllDrivers() throws ServiceException;
    Map<Driver,Integer> findAllAvailableDrivers(int hoursWorked) throws ServiceException;

}
