package com.jschool.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by infinity on 12.02.16.
 */
public class DriverController {

    /manager/driver/
    public void showTrucks(HttpServletRequest req, HttpServletResponse resp){

    }
    /manager/driver/add GET
    public void showFormForTruckAdd(HttpServletRequest req, HttpServletResponse resp){

    }
    /manager/driver/add POST
    public void addTruck(HttpServletRequest req, HttpServletResponse resp){

    }

    /manager/driver/{number}/delete GET
    public void deleteTruck(HttpServletRequest req, HttpServletResponse resp){

    }

    /manager/driver/{number}/change GET
    public void showFormFroChangeTruck(HttpServletRequest req, HttpServletResponse resp){

    }

    /manager/driver/change POST
    public void changeCargo(HttpServletRequest req, HttpServletResponse resp){

    }
}
