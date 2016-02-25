package com.jschool;

import com.jschool.controllers.*;


/**
 * Created by infinity on 12.02.16.
 */
public class ControllerFactory {

    private AppContext appContext = AppContext.getInstance();
    private DriverController driverController = appContext.getDriverController();
    private OrderController orderController = appContext.getOrderController();
    private TruckController truckController = appContext.getTruckController();
    private LoginController loginController = appContext.getLoginController();
    private DriverInfoController driverInfoController = appContext.getDriverInfoController();
    private ErrorController errorController = appContext.getErrorController();


    public BaseController getControllerByUri(String uri){
        if (uri.startsWith("/logiweb/employee/driver"))
            return driverController;
        else if (uri.startsWith("/logiweb/employee/order"))
            return orderController;
        else if (uri.startsWith("/logiweb/employee/truck"))
            return truckController;
        else if (uri.startsWith("/logiweb/log"))
            return loginController;
        else if (uri.startsWith("/logiweb/driver"))
            return driverInfoController;
        return errorController;
    }
}
