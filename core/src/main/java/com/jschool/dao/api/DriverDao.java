package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface DriverDao extends GenericDao<Driver> {

    Driver findUniqueByNumber(int number) throws DaoException;
    List<Driver> findAllByFirstNameAndLastName(String firstName, String lastName) throws DaoException;
    List<Driver> findAll() throws DaoException;
    List<Driver> findAllFreeDrivers(String city) throws DaoException;
    List<Driver> findAllByOffset(int offset, int limit) throws DaoException;
}
