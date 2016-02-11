package com.jschool.services.api;

import com.jschool.entities.Driver;
import com.jschool.entities.DriverStatus;
import com.jschool.entities.User;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface DriverService {
    /**
     * Создание нового пользователя
     */
    void create(Driver driver, User user);
    /**
     * Изменяем пользователя
     */
    void update(Driver driver, User user);
    /**
     * Удаляем пользователя не разрешаем удалить пока на заказе
     */
    void delete(int number);
    Driver findUniqueByNumber(int number);
    List<Driver> findAll();
    /**
     * Устанавливаем статус водителя, если он сменился с "за рулем"
     * на любой другой высчитываем отработанное время и заносим в базу
     */
    void setStatusByDriverNumberAndStatus(int number, DriverStatus status);

}
