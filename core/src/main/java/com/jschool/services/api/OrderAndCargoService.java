package com.jschool.services.api;

import com.jschool.entities.*;
import com.jschool.services.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface OrderAndCargoService {

    void addOrder(Order order, List<Cargo> cargos, int duration, int maxWeight) throws ServiceException;
    void updateOrder(Order order) throws ServiceException;
    void deleteOrder(int number) throws ServiceException;
    List<Order> findAllOrders() throws ServiceException;
    Order getOrderByNumber(int number) throws ServiceException;
    List<Cargo> findAllCargosByOrderNumber(int number) throws ServiceException;
    List<RoutePoint> findAllRoutePointsByOrderNumber(Order order) throws ServiceException;
    Truck getAssignedTruckByOrderNumber(int orderNumber) throws ServiceException;
    List<Driver> getAllAssignedDriversByOrderNumber(int orderNumber) throws ServiceException;
    List<String> getMaxWeight(String[] cargoWeight, String[] pickup, String[] unload);
    void fillRoute(List<String> cities, List<Integer> countOfUse, String[] pickupCity, String[] unloadCity);
}
