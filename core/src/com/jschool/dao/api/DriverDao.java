package com.jschool.dao.api;

import com.jschool.entities.Driver;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface DriverDao extends GenericDao<Driver> {

    Driver findUniqueByNumber(int number);
    List<Driver> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<Driver> findAll();
}
