package com.jschool.services.api;

import com.jschool.entities.CargoStatus;
import com.jschool.services.api.exception.ServiceExeption;

/**
 * Created by infinity on 13.02.16.
 */
public interface OrderManagementService {

    void changeCargoStatusByNumber(int cargoNumber, CargoStatus cargoStatus) throws ServiceExeption;
}
