package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverAuthCode;

import java.util.List;

/**
 * Created by infinity on 19.03.16.
 */
public interface DriverAuthCodeDao extends GenericDao<DriverAuthCode>{

    List<DriverAuthCode> findLastCode(Driver driver) throws DaoException;
}
