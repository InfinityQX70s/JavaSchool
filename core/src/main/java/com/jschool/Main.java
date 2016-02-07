package com.jschool;

import com.jschool.dao.api.CargoDao;
import com.jschool.dao.impl.CargoDaoImpl;
import com.jschool.entities.CargoEntity;
import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Main");
        CargoDao cargoDao = new CargoDaoImpl();
        List<CargoEntity> cargoEntity = cargoDao.findAll();
        for (CargoEntity a : cargoEntity)
            System.out.println(a.getName());
    }
}
