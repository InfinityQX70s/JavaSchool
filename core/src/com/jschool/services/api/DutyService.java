package com.jschool.services.api;

import com.jschool.entities.DriverStatus;
import com.jschool.services.api.exception.ServiceException;

/**
 * Created by infinity on 13.02.16.
 */
public interface DutyService {

    void loginDriverByNumber(int number,DriverStatus dutyStatus) throws ServiceException;
    void changeDriverDutyStatusByNumber(int number, DriverStatus dutyStatus) throws ServiceException;
    void logoutDriverByNumber(int number) throws ServiceException;
}
