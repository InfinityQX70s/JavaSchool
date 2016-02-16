package com.jschool.controllers;

import com.jschool.AppContext;
import com.jschool.entities.*;
import com.jschool.services.api.DriverService;
import com.jschool.services.api.OrderAndCargoService;
import com.jschool.services.api.TruckService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 12.02.16.
 */
public class OrderController implements Command{

    private AppContext appContext = AppContext.getInstance();
    private OrderAndCargoService orderAndCargoService = appContext.getOrderAndCargoService();
    private TruckService truckService = appContext.getTruckService();
    private DriverService driverService = appContext.getDriverService();

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");
        if (request.getMethod().equals("GET")){
            if (uri.length == 4 && uri[3].equals("orders"))
                showOrders(request,response);
            else if (uri.length == 5 && uri[4].equals("add"))
                showFormForOrderAdd(request,response);
        }
        if (request.getMethod().equals("POST")){
            if (uri.length == 5 && uri[4].equals("add"))
                addOrder(request,response);
            else if (uri.length == 5 && uri[4].equals("map"))
                mapHandler(request,response);
            else if (uri.length == 5 && uri[4].equals("truck"))
                assignTruck(request,response);
            else if (uri.length == 6 && uri[5].equals("driver"))
                assignDriver(request,response);
        }
    }

    // /employee/orders/
    public void showOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/order/order.jsp").forward(req,resp);
    }

    // /employee/order/add GET
    public void showFormForOrderAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/order/orderAdd.jsp").forward(req,resp);
    }
    //   /employee/order/add POST
    public void addOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String orderNumber = req.getParameter("orderNumber");
        String cargoNumber[] = req.getParameterValues("cargoNumber");
        String cargoName[] = req.getParameterValues("cargoName");
        String cargoWeight[] = req.getParameterValues("cargoWeight");
        String pickup[] = req.getParameterValues("pickup");
        String unload[] = req.getParameterValues("unload");
        Order order = new Order();
        order.setNumber(Integer.parseInt(orderNumber));
        order.setDoneState(false);
        orderAndCargoService.addOrder(order);
        List<String> cities = new ArrayList<>();
        for (int i = 0; i < cargoNumber.length; i++){
            Cargo cargo = new Cargo();
            cargo.setNumber(Integer.parseInt(cargoNumber[i]));
            cargo.setName(cargoName[i]);
            cargo.setWeight(Integer.parseInt(cargoWeight[i]));
            City pickCity = new City();
            cities.add(pickup[i]);
            pickCity.setName(pickup[i]);
            City unloadCity =  new City();
            cities.add(unload[i]);
            unloadCity.setName(unload[i]);
            RoutePoint pickRoute = new RoutePoint();
            pickRoute.setPoint(i);
            pickRoute.setCity(pickCity);
            RoutePoint unloadRoute = new RoutePoint();
            unloadRoute.setPoint(i);
            unloadRoute.setCity(unloadCity);
            cargo.setPickup(pickRoute);
            cargo.setUnload(unloadRoute);
            orderAndCargoService.addCargo(order.getNumber(),cargo);
        }
        req.setAttribute("cities",cities);
        req.setAttribute("order",order.getNumber());
        req.getRequestDispatcher("/WEB-INF/pages/order/orderMap.jsp").forward(req,resp);
    }

    // /employee/order/map POST
    public void mapHandler(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderNumber = req.getParameter("number");
        String duration = req.getParameter("duration").split(" ")[0];
        List<Cargo> cargos = orderAndCargoService.findAllCargosByOrderNumber(Integer.parseInt(orderNumber));
        int max = 0;
        for (Cargo cargo : cargos){
            if (cargo.getWeight() > max)
                max = cargo.getWeight();
        }
        List<Truck> trucks =truckService.findAllAvailableTrucksByMinCapacity(max);
        req.setAttribute("orderNumber",orderNumber);
        req.setAttribute("duration",duration);
        req.setAttribute("trucks",trucks);
        req.setAttribute("max",max);
        req.getRequestDispatcher("/WEB-INF/pages/order/selectTruck.jsp").forward(req,resp);
    }

    // /employee/order/truck POST
    public void assignTruck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String duration = req.getParameter("duration");
        String truckNumber = req.getParameter("truckNumber");
        String orderNumber = req.getParameter("orderNumber");
        orderAndCargoService.assignTruckToOrder(truckNumber,Integer.parseInt(orderNumber));
        List<Driver> drivers = driverService.findAllAvailableDrivers(Integer.parseInt(duration));
        req.setAttribute("drivers",drivers);
        req.setAttribute("duration",duration);
        req.getRequestDispatcher("/WEB-INF/pages/order/selectDriver.jsp").forward(req,resp);
        //:TODO  список по размеру смены !!!
    }


    // /employee/order/driver POST
    public void assignDriver(HttpServletRequest req, HttpServletResponse resp){

    }

}
