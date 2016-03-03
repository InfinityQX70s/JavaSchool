package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Truck;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface TruckDao extends GenericDao<Truck> {

    Truck findUniqueByNumber(String number) throws DaoException;
    List<Truck> findAllFreeByStateAndGreaterThanCapacity(boolean isRepair, int capacity) throws DaoException;
    List<Truck> findAll() throws DaoException;
    List<Truck> findAllByOffset(int offset, int limit) throws DaoException;
}
