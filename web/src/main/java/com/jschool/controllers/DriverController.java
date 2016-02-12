package com.jschool.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 12.02.16.
 */
public class DriverController implements Command{

//    /manager/driver/
    public void showDrivers(HttpServletRequest req, HttpServletResponse resp){

    }
 //   /manager/driver/add GET
    public void showFormForDriverAdd(HttpServletRequest req, HttpServletResponse resp){

    }
 //   /manager/driver/add POST
    public void addDriver(HttpServletRequest req, HttpServletResponse resp){

    }

 //   /manager/driver/delete/{number} GET
    public void deleteDriver(HttpServletRequest req, HttpServletResponse resp){

    }

 //   /manager/driver/{number} GET
    public void showFormForChangeDriver(HttpServletRequest req, HttpServletResponse resp){

    }

 //   /manager/driver/change POST
    public void changeDriver(HttpServletRequest req, HttpServletResponse resp){

    }

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
