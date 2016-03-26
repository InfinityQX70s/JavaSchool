package com.jschool.dao.impl;

import com.jschool.dao.api.TruckStatisticDao;
import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Truck;
import com.jschool.entities.TruckStatistic;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 26.03.16.
 */
@Repository
public class TruckStatisticDaoImpl extends GenericDaoImpl<TruckStatistic> implements TruckStatisticDao {

    private static final Logger LOG = Logger.getLogger(TruckStatisticDaoImpl.class);

    @Override
    public List<TruckStatistic> findAllByOneMonth(Truck truck) throws DaoException {
        try {
            TypedQuery<TruckStatistic> query =
                    entityManager.createNamedQuery("TruckStatistic.findAllByOneMonth", TruckStatistic.class);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, -1);
            Date endDate = calendar.getTime();
            query.setParameter("truck", truck);
            query.setParameter("endDate", endDate, TemporalType.DATE);
            return query.getResultList();
        }catch (Exception e){
            LOG.error("Unexpected DB exception", e);
            throw  new DaoException(e);
        }
    }
}
