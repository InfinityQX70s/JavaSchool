package com.jschool.services.api;

import com.jschool.entities.Truck;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface TruckService {

     void create(Truck truck);
     void update(Truck truck);
    /**
     * Удалить нельзя пока на заказе
     */
     void delete(String truckNumber);
     Truck findByNumber(String number);
     List<Truck> findAll();
    /**
     * Список фур, подходящих по вместимости
     */
     List<Truck> findAllFreeByStateAndGreaterThanCapacity(boolean isRepair, int capacity);
}
