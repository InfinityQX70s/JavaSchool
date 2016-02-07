package com.jschool.dao.api;

import com.jschool.entities.TruckEntity;

import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public interface TruckDao extends GenericDao<TruckEntity> {

    List<TruckEntity> findAllByStateAndCapacity(boolean isRepair,int capacity);
    List<TruckEntity> findAll();
}
