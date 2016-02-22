package com.jschool.dao.impl;

import com.jschool.dao.api.DriverStatusLogDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatusLog;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class DriverStatusLogDaoImpl extends GenericDaoImpl<DriverStatusLog> implements DriverStatusLogDao{

    private static final Logger LOG = Logger.getLogger(DriverStatusLogDaoImpl.class);

    public DriverStatusLogDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public DriverStatusLog findLastStatus(Driver driver) throws DaoException {
        try {
            TypedQuery<DriverStatusLog> query =
                    entityManager.createNamedQuery("DriverStatusLog.findLastStatus", DriverStatusLog.class);
            query.setParameter("driver", driver);
            query.setMaxResults(1);
            List<DriverStatusLog> driverStatusLogs = query.getResultList();
            DriverStatusLog driverStatusLog = null;
            if (!driverStatusLogs.isEmpty())
                driverStatusLog = driverStatusLogs.get(0);
            return driverStatusLog;
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
