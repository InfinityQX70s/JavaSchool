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

    private static final String EMP_DRIVER = "/logiweb/employee/driver";
    private static final String EMP_ORDER = "/logiweb/employee/order";
    private static final  String EMP_TRUCK = "/logiweb/employee/truck";
    private static final String LOGIN = "/logiweb/log";
    private static final String DRIVER = "/logiweb/driver";


    public BaseController getControllerByUri(String uri){
        if (uri.startsWith(EMP_DRIVER))
            return driverController;
        else if (uri.startsWith(EMP_ORDER))
            return orderController;
        else if (uri.startsWith(EMP_TRUCK))
            return truckController;
        else if (uri.startsWith(LOGIN))
            return loginController;
        else if (uri.startsWith(DRIVER))
            return driverInfoController;
        return errorController;
    }
}
