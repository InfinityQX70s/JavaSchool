package com.jschool;

import com.jschool.controllers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by infinity on 12.02.16.
 */
public class CommandFactory {

    private static DriverController driverController = new DriverController();
    private static OrderController orderController = new OrderController();
    private static TruckController truckController = new TruckController();
    private static LoginController loginController = new LoginController();
    private static BaseController baseController = new BaseController();


    public static Command create(HttpServletRequest request){
        String url = request.getRequestURI();
        if (url.startsWith("/manager/driver"))
            return driverController;
        if (url.startsWith("/manager/order/"))
            return orderController;
        if (url.startsWith("/manager/truck"))
            return truckController;
        if (url.startsWith("/css") || url.startsWith("/js") || url.startsWith("/font") || url.startsWith("/image"))
            return baseController;
        return loginController;
    }
}
