package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Cargo;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface CargoDao extends GenericDao<Cargo> {

    Cargo findUniqueByNumber(int number) throws DaoException;
    List<Cargo> findAllByName(String name) throws DaoException;
    List<Cargo> findAll() throws DaoException;
}
