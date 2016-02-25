package com.jschool.dao.impl;

import com.jschool.dao.api.DriverStatisticDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatistic;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class DriverStatisticDaoImpl extends GenericDaoImpl<DriverStatistic> implements DriverStatisticDao{

    private static final Logger LOG = Logger.getLogger(DriverStatisticDaoImpl.class);

    public DriverStatisticDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<DriverStatistic> findAllByOneMonth(Driver driver) throws DaoException {
        try {
            TypedQuery<DriverStatistic> query =
                    entityManager.createNamedQuery("DriverStatistic.findAllByOneMonth", DriverStatistic.class);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, -1);
            Date endDate = calendar.getTime();
            query.setParameter("driver", driver);
            query.setParameter("endDate", endDate, TemporalType.DATE);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
