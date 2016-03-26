package com.jschool.dao.api;

import com.jschool.dao.api.exception.DaoException;
import com.jschool.entities.Truck;
import com.jschool.entities.TruckStatistic;

import java.util.List;

/**
 * Created by infinity on 26.03.16.
 */
public interface TruckStatisticDao extends GenericDao<TruckStatistic> {

    List<TruckStatistic> findAllByOneMonth(Truck truck) throws DaoException;
}
