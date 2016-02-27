package com.jschool.services.api;

import com.jschool.entities.Cargo;
import com.jschool.entities.Driver;
import com.jschool.entities.Order;
import com.jschool.entities.Truck;
import com.jschool.services.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface OrderAndCargoService {

    void addOrder(Order order, List<Cargo> cargos, int duration) throws ServiceException;
    void updateOrder(Order order) throws ServiceException;
    void deleteOrder(int number) throws ServiceException;
    List<Order> findAllOrders() throws ServiceException;
    Order getOrderByNumber(int number) throws ServiceException;
    List<Cargo> findAllCargosByOrderNumber(int number) throws ServiceException;
    Truck getAssignedTruckByOrderNumber(int orderNumber) throws ServiceException;
    List<Driver> getAllAssignedDriversByOrderNumber(int orderNumber) throws ServiceException;

}
