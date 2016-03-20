package com.jschool.rest;

import com.jschool.entities.*;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.DutyService;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.OrderManagementService;
import com.jschool.services.api.exception.ServiceException;
import com.sun.xml.internal.ws.api.message.Packet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogiwebRestController {

    private static final Logger LOG = Logger.getLogger(LogiwebRestController.class);

    private DutyService dutyService;
    private DriverService driverService;
    private OrderAndCargoService orderAndCargoService;
    private OrderManagementService orderManagementService;

    @Autowired
    public LogiwebRestController(OrderAndCargoService orderAndCargoService,DriverService driverService, DutyService dutyService, OrderManagementService orderManagementService) {
        this.dutyService = dutyService;
        this.orderAndCargoService = orderAndCargoService;
        this.driverService = driverService;
        this.orderManagementService = orderManagementService;
    }

    @RequestMapping(value = "/rs/driver/{number}", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse loginDriverAssignToOrder(@PathVariable("number") int number) {
        try {
            Driver driver = driverService.getDriverByPersonalNumber(number);
            if (driver != null && driver.getOrder() != null) {
                JsonResponse jsonResponse = new JsonResponse();
                jsonResponse.setStatus("ok");
                jsonResponse.setResult("Driver " + String.valueOf(number) + " successfully login and has order");
                return jsonResponse;
            } else {
                return getErrorObject("Driver don't assign at order");
            }
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return getErrorObject(e.getMessage());
        }
    }

    @RequestMapping(value = "/rs/driver/{number}/order", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse getNextPoint(@PathVariable("number") int number) {
        try {
            JsonResponse jsonResponse = new JsonResponse();
            Driver driver = driverService.getDriverByPersonalNumber(number);
            Order order = driver.getOrder();
            if (order == null){
                jsonResponse.setStatus("done");
                return jsonResponse;
            }else{
                jsonResponse.setStatus("ok");
                List<RoutePoint> routePoints = orderAndCargoService.findAllRoutePointsByOrderNumber(order);
                for (RoutePoint routePoint : routePoints){
                    if (routePoint.getPickup()!= null){
                        Cargo cargo = routePoint.getPickup();
                        CargoStatus cargoStatus = cargo.getStatusLogs().get(cargo.getStatusLogs().size()-1).getStatus();
                        if (cargoStatus == CargoStatus.ready){
                            jsonResponse.setCargoName(cargo.getName());
                            jsonResponse.setCargoNumber(cargo.getNumber());
                            jsonResponse.setCity(cargo.getPickup().getCity().getName());
                            jsonResponse.setType("loaded");
                            return jsonResponse;
                        }
                    }
                    if (routePoint.getUnload()!= null){
                        Cargo cargo = routePoint.getUnload();
                        CargoStatus cargoStatus = cargo.getStatusLogs().get(cargo.getStatusLogs().size()-1).getStatus();
                        if (cargoStatus == CargoStatus.loaded){
                            jsonResponse.setCargoName(cargo.getName());
                            jsonResponse.setCargoNumber(cargo.getNumber());
                            jsonResponse.setCity(cargo.getUnload().getCity().getName());
                            jsonResponse.setType("delivered");
                            return jsonResponse;
                        }
                    }
                }
                jsonResponse.setStatus("done");
                return jsonResponse;
            }
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return getErrorObject(e.getMessage());
        }
    }



    @RequestMapping(value = "/rs/driver/{number}/shift", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse setShiftDriverStatus(@PathVariable("number") int number) {
        try {
            dutyService.loginDriverByNumber(number, DriverStatus.shift);
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setStatus("ok");
            jsonResponse.setResult("Driver " + String.valueOf(number) + " set shift status successfully");
            return jsonResponse;
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return getErrorObject(e.getMessage());
        }
    }

    @RequestMapping(value = "/rs/driver/{number}/rest", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse setRestDriverStatus(@PathVariable("number") int number) {
        try {
            dutyService.logoutDriverByNumber(number);
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setStatus("ok");
            jsonResponse.setResult("Driver " + String.valueOf(number) + " set rest status successfully");
            return jsonResponse;
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return getErrorObject(e.getMessage());
        }
    }

    @RequestMapping(value = "/rs/driver/{number}/driving", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse setDrivingDriverStatus(@PathVariable("number") int number) {
        try {
            dutyService.changeDriverDutyStatusByNumber(number, DriverStatus.driving);
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setStatus("ok");
            jsonResponse.setResult("Driver " + String.valueOf(number) + " set driving status successfully");
            return jsonResponse;
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return getErrorObject(e.getMessage());
        }
    }

    @RequestMapping(value = "/rs/cargo/{number}/delivered", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse setCargoStatusDelivered(@PathVariable("number") int number) {
        try {
            orderManagementService.changeCargoStatusByNumber(number, CargoStatus.delivered);
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setStatus("ok");
            jsonResponse.setResult("Cargo " + String.valueOf(number) + " set delivered status successfully");
            return jsonResponse;
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return getErrorObject(e.getMessage());
        }
    }

    @RequestMapping(value = "/rs/cargo/{number}/loaded", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse setCargoStatusLoaded(@PathVariable("number") int number) {
        try {
            orderManagementService.changeCargoStatusByNumber(number, CargoStatus.loaded);
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setStatus("ok");
            jsonResponse.setResult("Cargo " + String.valueOf(number) + " set loaded status successfully");
            return jsonResponse;
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return getErrorObject(e.getMessage());
        }
    }

    private JsonResponse getErrorObject(String massage) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatus("error");
        jsonResponse.setResult(massage);
        return jsonResponse;
    }
}
