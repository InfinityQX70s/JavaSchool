package com.jschool;

import com.jschool.controllers.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by infinity on 12.02.16.
 */
public class CommandFactory {

    private static DriverController driverController = new DriverController();
    private static OrderController orderController = new OrderController();
    private static TruckController truckController = new TruckController();
    private static LoginController loginController = new LoginController();


    public static Command create(HttpServletRequest request){
        String uri = request.getRequestURI();
        if (uri.startsWith("/logiweb/employee/driver"))
            return driverController;
        if (uri.startsWith("/logiweb/employee/order"))
            return orderController;
        if (uri.startsWith("/logiweb/employee/truck"))
            return truckController;
        return loginController;
    }
}
