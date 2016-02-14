package com.jschool.services.api;

import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatus;
import com.jschool.entities.User;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface DriverService {

    void addDriver(Driver driver);
    void updateDrive(Driver driver);
    void deleteDriver(int number);
    Driver getDriverByPersonalNumber(int number);
    List<Driver> findAllDrivers();
    List<Driver> findAllAvailableDrivers(int hoursWorked);

}
