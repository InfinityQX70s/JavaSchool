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
    private static DriverInfoController driverInfoController = new DriverInfoController();


    public static Command create(HttpServletRequest request){
        String uri = request.getRequestURI();
        if (uri.startsWith("/logiweb/employee/driver"))
            return driverController;
        else if (uri.startsWith("/logiweb/employee/order"))
            return orderController;
        else if (uri.startsWith("/logiweb/employee/truck"))
            return truckController;
        else if (uri.startsWith("/logiweb/log"))
            return loginController;
        return null;
    }
}
