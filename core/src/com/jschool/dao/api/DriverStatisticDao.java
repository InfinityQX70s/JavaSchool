package com.jschool.dao.api;

import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatistic;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface DriverStatisticDao extends GenericDao<DriverStatistic> {

    List<DriverStatistic> findAllByOneMonth(Driver driver);
}
