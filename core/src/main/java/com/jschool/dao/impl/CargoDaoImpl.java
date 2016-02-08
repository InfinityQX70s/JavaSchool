package com.jschool.dao.impl;

import com.jschool.dao.api.CargoDao;
import com.jschool.dao.api.CargoStatusLogDao;
import com.jschool.entities.CargoEntity;
import com.jschool.entities.CargoStatusLogEntity;
import com.jschool.entities.status.CargoStatus;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by infinity on 07.02.16.
 */
public class CargoDaoImpl extends GenericDaoImpl<CargoEntity> implements CargoDao {

    public CargoEntity findByNumber(int number) {
        TypedQuery<CargoEntity> query =
                entityManager.createNamedQuery("Cargo.findByNumber", CargoEntity.class);
        query.setParameter("number", number);
        return query.getSingleResult();
    }

    public CargoEntity findByName(String name) {
        TypedQuery<CargoEntity> query =
                entityManager.createNamedQuery("Cargo.findByName", CargoEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<CargoEntity> findAll() {
        TypedQuery<CargoEntity> query =
                entityManager.createNamedQuery("Cargo.findAll", CargoEntity.class);
        return query.getResultList();
    }

    @Override
    public void create(CargoEntity entity) {
        entityManager.getTransaction( ).begin( );
        entityManager.persist(entity);
        CargoStatusLogEntity cargoStatusLogEntity = new CargoStatusLogEntity();
        cargoStatusLogEntity.setStatus(CargoStatus.ready);
        cargoStatusLogEntity.setTimestamp(new Date());
        cargoStatusLogEntity.setCargo(entity);
        entityManager.persist(cargoStatusLogEntity);
        entityManager.getTransaction( ).commit( );
    }

    public void setCargoStatus(int number, CargoStatus status){
        entityManager.getTransaction( ).begin( );
        CargoEntity cargo = findByNumber(number);
        CargoStatusLogEntity cargoStatusLogEntity = new CargoStatusLogEntity();
        cargoStatusLogEntity.setStatus(CargoStatus.delivered);
        cargoStatusLogEntity.setTimestamp(new Date());
        cargoStatusLogEntity.setCargo(cargo);
        entityManager.persist(cargoStatusLogEntity);
        entityManager.getTransaction( ).commit( );
    }
}
