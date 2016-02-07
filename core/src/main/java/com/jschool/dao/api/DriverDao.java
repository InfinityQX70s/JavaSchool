package com.jschool.dao.api;

import com.jschool.entities.DriverEntity;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface DriverDao extends GenericDao<DriverEntity> {

    DriverEntity findByNumber(int number);
    List<DriverEntity> findByFirstNameAndLastName(String firstName, String lastName);
    List<DriverEntity> findAll();
}
