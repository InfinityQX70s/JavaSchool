package com.jschool.services.api;

import com.jschool.entities.DriverStatus;

/**
 * Created by infinity on 13.02.16.
 */
public interface DutyService {

    void loginDriverByNumber(int number,DriverStatus dutyStatus);
    void changeDriverDutyStatusByNumber(int number, DriverStatus dutyStatus);
    void logoutDriverByNumber(int number);
}
