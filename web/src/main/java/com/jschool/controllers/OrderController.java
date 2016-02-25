package com.jschool.controllers;

import com.jschool.AppContext;
import com.jschool.Validator;
import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.jschool.entities.*;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by infinity on 12.02.16.
 */
public class OrderController implements BaseController {

    private static final Logger LOG = Logger.getLogger(OrderController.class);

    private AppContext appContext = AppContext.getInstance();
    private OrderAndCargoService orderAndCargoService = appContext.getOrderAndCargoService();
    private TruckService truckService = appContext.getTruckService();
    private DriverService driverService = appContext.getDriverService();
    private Validator validator = appContext.getValidator();

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if ("GET".equals(request.getMethod())) {
                if (uri.length == 4 && "orders".equals(uri[3]))
                    showOrders(request, response);
                else if (uri.length == 5 && "add".equals(uri[4]))
                    showFormForOrderAdd(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if ("POST".equals(request.getMethod())) {
                if (uri.length == 5 && "add".equals(uri[4]))
                    addOrder(request, response);
                else if (uri.length == 5 && "submit".equals(uri[4]))
                    submitOrder(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    // /employee/orders/
    public void showOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Order> orders = orderAndCargoService.findAllOrders();
            Map<Order, List<Cargo>> orderListMap = new HashMap<>();
            for (Order order : orders) {
                List<Cargo> cargos = orderAndCargoService.findAllCargosByOrderNumber(order.getNumber());
                orderListMap.put(order, cargos);
            }
            req.setAttribute("orderListMap", orderListMap);
            req.getRequestDispatcher("/WEB-INF/pages/order/order.jsp").forward(req, resp);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    // /employee/order/add GET
    public void showFormForOrderAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/order/orderAllAdd.jsp").forward(req, resp);
    }

    //   /employee/order/add POST
    public void addOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String stepNumber = req.getParameter("step_number");
            switch (stepNumber) {
                case "1":
                    req.getRequestDispatcher("/WEB-INF/pages/order/orderCargo.jsp").forward(req, resp);
                    break;
                case "2":
                    String orderNumber = req.getParameter("orderNumber");
                    String[] cargoNumber = req.getParameterValues("cargoNumber");
                    String[] cargoName = req.getParameterValues("cargoName");
                    String[] cargoWeight = req.getParameterValues("cargoWeight");
                    String[] pickup = req.getParameterValues("pickup");
                    String[] unload = req.getParameterValues("unload");
                    validator.validateOrderAndCargo(orderNumber, cargoNumber, cargoName, cargoWeight, pickup, unload);
                    int max = 0;
                    for (String weight : cargoWeight) {
                        if (Integer.parseInt(weight) > max)
                            max = Integer.parseInt(weight);
                    }
                    List<Truck> trucks = truckService.findAllAvailableTrucksByMinCapacity(max);
                    req.setAttribute("trucks", trucks);
                    req.setAttribute("max", max);
                    req.getRequestDispatcher("/WEB-INF/pages/order/orderTruck.jsp").forward(req, resp);
                    break;
                case "3":
                    String pickupCity[] = req.getParameterValues("pickup");
                    String unloadCity[] = req.getParameterValues("unload");
                    validator.validateCargoCities(pickupCity, unloadCity);
                    List<String> cities = new ArrayList<>();
                    for (int i = 0; i < pickupCity.length; i++) {
                        cities.add(pickupCity[i]);
                        cities.add(unloadCity[i]);
                    }
                    req.setAttribute("cities", cities);
                    req.getRequestDispatcher("/WEB-INF/pages/order/orderMap.jsp").forward(req, resp);
                    break;
                case "4":
                    String duration = req.getParameter("duration").split(" ")[0];
                    String truckNumber = req.getParameter("truckNumber");
                    validator.validateTruckNumber(truckNumber);
                    Truck truck = truckService.getTruckByNumber(truckNumber);
                    Map<Driver, Integer> driverHoursList = driverService.findAllAvailableDrivers(Integer.parseInt(duration));
                    req.setAttribute("drivers", driverHoursList);
                    req.setAttribute("duration", duration);
                    req.setAttribute("shiftSize", truck.getShiftSize());
                    req.getRequestDispatcher("/WEB-INF/pages/order/orderDrivers.jsp").forward(req, resp);
                    break;
            }
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    // /employee/order/submit POST
    public void submitOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String orderNumber = req.getParameter("orderNumber");
            String[] cargoNumber = req.getParameterValues("cargoNumber");
            String[] cargoName = req.getParameterValues("cargoName");
            String[] cargoWeight = req.getParameterValues("cargoWeight");
            String[] pickup = req.getParameterValues("pickup");
            String[] unload = req.getParameterValues("unload");
            String truckNumber = req.getParameter("truckNumber");
            String[] driverNumbers = req.getParameterValues("driverNumber");
            validator.validateOrderAndCargo(orderNumber,cargoNumber,cargoName,cargoWeight,pickup,unload);
            validator.validateTruckAndDrivers(truckNumber,driverNumbers);

            Order order = new Order();
            order.setNumber(Integer.parseInt(orderNumber));
            order.setDoneState(false);
            List<Cargo> cargos = new ArrayList<>();
            for (int i = 0; i < cargoNumber.length; i++) {
                Cargo cargo = new Cargo();
                cargo.setNumber(Integer.parseInt(cargoNumber[i]));
                cargo.setName(cargoName[i]);
                cargo.setWeight(Integer.parseInt(cargoWeight[i]));

                City pickCity = new City();
                pickCity.setName(pickup[i]);

                City unloadCity = new City();
                unloadCity.setName(unload[i]);

                RoutePoint pickRoute = new RoutePoint();
                pickRoute.setPoint(i);
                pickRoute.setCity(pickCity);

                RoutePoint unloadRoute = new RoutePoint();
                unloadRoute.setPoint(i);
                unloadRoute.setCity(unloadCity);

                cargo.setPickup(pickRoute);
                cargo.setUnload(unloadRoute);
                cargos.add(cargo);
            }
            Truck truck = truckService.getTruckByNumber(truckNumber);
            List<Driver> drivers = new ArrayList<>();
            for (String driver : driverNumbers)
                drivers.add(driverService.getDriverByPersonalNumber(Integer.parseInt(driver)));
            order.setTruck(truck);
            order.setDrivers(drivers);
            orderAndCargoService.addOrder(order, cargos);
            resp.sendRedirect("/employee/orders");
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

}
