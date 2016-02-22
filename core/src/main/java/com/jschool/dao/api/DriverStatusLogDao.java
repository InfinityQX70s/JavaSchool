package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatusLog;

/**
 * Created by infinity on 07.02.16.
 */
public interface DriverStatusLogDao extends GenericDao<DriverStatusLog> {

    DriverStatusLog findLastStatus(Driver driver) throws DaoException;
}
