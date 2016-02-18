package com.jschool.controllers;

import com.jschool.AppContext;
import com.jschool.entities.Truck;
import com.jschool.services.api.TruckService;
import com.jschool.services.api.exception.ServiceExeption;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by infinity on 12.02.16.
 */
public class TruckController implements Command{

    private AppContext appContext = AppContext.getInstance();
    private TruckService truckService = appContext.getTruckService();

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");
        if (request.getMethod().equals("GET")){
            if (uri.length == 4 && uri[3].equals("trucks"))
                showTrucks(request,response);
            else if (uri.length == 5 && uri[4].equals("add"))
                showFormForTruckAdd(request,response);
            else if (uri.length == 6 && uri[5].equals("edit"))
                showFormForChangeTruck(request,response,uri[4]);
        }
        if (request.getMethod().equals("POST")){
            if (uri.length == 5 && uri[4].equals("add"))
                addTruck(request,response);
            else if (uri.length == 5 && uri[4].equals("delete"))
                deleteTruck(request,response);
            else if (uri.length == 5 && uri[4].equals("change"))
                changeTruck(request,response);
        }
    }


   // /employee/trucks/
    public void showTrucks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Truck> trucks = truckService.findAllTrucks();
            req.setAttribute("trucks",trucks);
            req.getRequestDispatcher("/WEB-INF/pages/truck/truck.jsp").forward(req, resp);
        }catch (ServiceExeption e){

        }
    }
   // /employee/truck/add GET
    public void showFormForTruckAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/truck/truckAdd.jsp").forward(req, resp);
    }
  //  /employee/truck/add POST
    public void addTruck(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String number = req.getParameter("number");
            String capacity = req.getParameter("capacity");
            String shiftSize = req.getParameter("shiftSize");
            String status = req.getParameter("status");
            Truck truck = new Truck();
            truck.setNumber(number);
            truck.setCapacity(Integer.parseInt(capacity));
            truck.setShiftSize(Integer.parseInt(shiftSize));
            if (status.equals("ok")){
                truck.setRepairState(true);
            }else {
                truck.setRepairState(false);
            }
            truckService.addTruck(truck);
            resp.sendRedirect("/employee/trucks");
        }catch (ServiceExeption e){

        }
    }

 //   /employee/truck/delete POST
    public void deleteTruck(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String number = req.getParameter("number");
            truckService.deleteTruck(number);
            resp.sendRedirect("/employee/trucks");
        }catch (ServiceExeption e){

        }
    }

 //   /employee/truck/{number}/edit GET
    public void showFormForChangeTruck(HttpServletRequest req, HttpServletResponse resp, String number) throws ServletException, IOException {
        try {
            Truck truck = truckService.getTruckByNumber(number);
            req.setAttribute("truck",truck);
            req.getRequestDispatcher("/WEB-INF/pages/truck/truckEdit.jsp").forward(req, resp);
        }catch (ServiceExeption e){

        }
    }

 //   /employee/truck/change POST
    public void changeTruck(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String number = req.getParameter("number");
            String capacity = req.getParameter("capacity");
            String shiftSize = req.getParameter("shiftSize");
            String status = req.getParameter("status");
            Truck truck = new Truck();
            truck.setNumber(number);
            truck.setCapacity(Integer.parseInt(capacity));
            truck.setShiftSize(Integer.parseInt(shiftSize));
            if (status.equals("ok")){
                truck.setRepairState(true);
            }else {
                truck.setRepairState(false);
            }
            truckService.updateTruck(truck);
            resp.sendRedirect("/employee/trucks");
        }catch (ServiceExeption e){

        }
    }

}
