package com.jschool.dao.api;

import com.jschool.entities.Cargo;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface CargoDao extends GenericDao<Cargo> {

    Cargo findUniqueByNumber(int number);
    List<Cargo> findAllByName(String name);
    List<Cargo> findAll();
}
