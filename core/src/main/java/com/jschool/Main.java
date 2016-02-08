package com.jschool;

import com.jschool.dao.api.CargoDao;
import com.jschool.dao.impl.CargoDaoImpl;
import com.jschool.dao.impl.UserDaoImpl;
import com.jschool.entities.CargoEntity;
import com.jschool.entities.UserEntity;
import com.jschool.services.impl.TruckServiceImpl;
import com.jschool.services.impl.UserServiceImpl;

import java.util.List;

/**
 * Created by infinity on 06.02.16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Main");
        UserServiceImpl userService = new UserServiceImpl();
        UserEntity userEntity = userService.findByEmail("ololo@driver.sto");
        userService.delete(userEntity);

    }
}
