package com.jschool.services.api;

import com.jschool.entities.Cargo;
import com.jschool.entities.Driver;
import com.jschool.entities.Order;
import com.jschool.entities.Truck;
import com.jschool.services.api.exception.ServiceExeption;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface OrderAndCargoService {

    void addOrder(Order order) throws ServiceExeption;
    void updateOrder(Order order) throws ServiceExeption;
    void deleteOrder(int number) throws ServiceExeption;
    List<Order> findAllOrders() throws ServiceExeption;
    Order getOrderByNumber(int number) throws ServiceExeption;
    void addCargo(int orderNumber, Cargo cargo) throws ServiceExeption;
    List<Cargo> findAllCargosByOrderNumber(int number) throws ServiceExeption;
    void assignTruckToOrder(String truckNumber, int orderNumber) throws ServiceExeption;
    Truck getAssignedTruckByOrderNumber(int orderNumber) throws ServiceExeption;
    void assignDriverToOrder(int driverNumber, int orderNumber) throws ServiceExeption;
    List<Driver> getAllAssignedDriversByOrderNumber(int orderNumber) throws ServiceExeption;

}
