package com.jschool.services.api;

import com.jschool.entities.Cargo;
import com.jschool.entities.Driver;
import com.jschool.entities.Order;
import com.jschool.entities.Truck;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface OrderService {

     void create(Order order);
    /**
     * Удалить нельзя пока не выполнен, возможно удалять и не нужно.
     */
     void delete(int number);
    /**
     * Создаем груз и routePoints для него и выставляем его статус как готово.
     */
     void createCargoAndAssignToOrder(int orderNumber, Cargo cargo, String pickupName, String unloadName, int point);
     List<Order> findAll();
     Order findUniqueByNumber(int number);
     List<Order> findAllByState(boolean isDone);
    /**
     * Находим все свободные траки у которых вместимость больше необходимой
     */
     List<Truck> findAllFreeTruckByStateAndGreaterThanCapacity(int capacity);
    /**
     * Занимаем Трак
     */
     void assignTruckToOrder(String truckNumber, int orderNumber);
    /**
     * Найти трак который выполняет заказ
     */
     Truck findUniqueAssignedTruckByOrderNumber(int orderNumber);
    /**
     * Привязываем водителя к заказу, с проверкой что водителей
     * привязано не более чем в размере смены
     */
     void assignDriverToOrder(int driverNumber, int orderNumber);
    /**
     * Список водителей выполняющих заказ
     */
     List<Driver> findAllAssignedDriversByOrderNumber(int orderNumber);
    /**
     * Находим всех подходящих водителей у которых смена меньше 176 часов
     */
     List<Driver> findAllFreeDriversByHoursWorked(int hoursWorked);
}
