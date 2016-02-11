package com.jschool.services.api;

import com.jschool.entities.Cargo;
import com.jschool.entities.CargoStatus;

import java.util.List;

/**
 * Created by infinity on 11.02.16.
 */
public interface CargoService {

    /**
     * Устанавливает статус груза и проверяет,
     * другие грузы в заказе уже доставлены,
     * если да, то ставит весь заказ в статус доставлено.
     * Затем освобождаем Трак и Водителей.
     *
     */
     void setCargoStatus(int number, CargoStatus status);
     Cargo findByNumber(int number);
     List<Cargo> findAll();
}
