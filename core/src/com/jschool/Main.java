package com.jschool;

import com.jschool.entities.Cargo;
import com.jschool.services.impl.CargoServiceImpl;

/**
 * Created by infinity on 06.02.16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Main");
        CargoServiceImpl a = new CargoServiceImpl();
        Cargo cargoEntity = new Cargo();
        cargoEntity.setNumber(84);
        cargoEntity.setName("Koks");
        cargoEntity.setWeight(20);
        a.create(cargoEntity);
    }
}
