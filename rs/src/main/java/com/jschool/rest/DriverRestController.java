package com.jschool.rest;

import com.jschool.entities.CargoStatus;
import com.jschool.entities.DriverStatus;
import com.jschool.services.api.DutyService;
import com.jschool.services.api.OrderManagementService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverRestController {

    private static final Logger LOG = Logger.getLogger(DriverRestController.class);

    private DutyService dutyService;
    private OrderManagementService orderManagementService;

    @Autowired
    public DriverRestController(DutyService dutyService, OrderManagementService orderManagementService) {
        this.dutyService = dutyService;
        this.orderManagementService = orderManagementService;
    }

    @RequestMapping(value = "/rs/driver/{number}/shift", method = RequestMethod.GET, produces = "application/json")
    public JsonResponse setShiftDriverStatus(@PathVariable("number") int number) {
        try {
            dutyService.loginDriverByNumber(number, DriverStatus.shift);
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setStatus("ok");
            jsonResponse.setResult("Driver " + String.valueOf(number) + "set shift status successfully");
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
            jsonResponse.setResult("Driver " + String.valueOf(number) + "set rest status successfully");
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
            jsonResponse.setResult("Driver " + String.valueOf(number) + "set driving status successfully");
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
            jsonResponse.setResult("Cargo " + String.valueOf(number) + "set delivered status successfully");
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
            jsonResponse.setResult("Cargo " + String.valueOf(number) + "set loaded status successfully");
            return jsonResponse;
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            return getErrorObject(e.getMessage());
        }
    }

    private JsonResponse getErrorObject(String massage){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatus("error");
        jsonResponse.setResult(massage);
        return jsonResponse;
    }
}
