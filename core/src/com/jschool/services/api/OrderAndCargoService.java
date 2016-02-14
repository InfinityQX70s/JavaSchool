package com.jschool.services.api;

import com.jschool.entities.Cargo;
import com.jschool.entities.Driver;
import com.jschool.entities.Order;
import com.jschool.entities.Truck;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface OrderAndCargoService {

    void addOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(int number);
    List<Order> findAllOrders();
    Order getOrderByNumber(int number);
    void addCargo(int orderNumber, Cargo cargo);
    List<Cargo> findAllCargosByOrderNumber(int number);
    void assignTruckToOrder(String truckNumber, int orderNumber);
    Truck getAssignedTruckByOrderNumber(int orderNumber);
    void assignDriverToOrder(int driverNumber, int orderNumber);
    List<Driver> getAllAssignedDriversByOrderNumber(int orderNumber);

}
