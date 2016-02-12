package com.jschool.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 12.02.16.
 */
public class TruckController implements Command{


   // /manager/truck/
    public void showTrucks(HttpServletRequest req, HttpServletResponse resp){

    }
   // /manager/truck/add GET
    public void showFormForTruckAdd(HttpServletRequest req, HttpServletResponse resp){

    }
  //  /manager/truck/add POST
    public void addTruck(HttpServletRequest req, HttpServletResponse resp){

    }

 //   /manager/truck/delete/{number} GET
    public void deleteTruck(HttpServletRequest req, HttpServletResponse resp){

    }

 //   /manager/truck/change/{number} GET
    public void showFormForChangeTruck(HttpServletRequest req, HttpServletResponse resp){

    }

 //   /manager/truck/change POST
    public void changeCargo(HttpServletRequest req, HttpServletResponse resp){

    }

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
