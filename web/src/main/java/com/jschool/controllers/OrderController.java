package com.jschool.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 12.02.16.
 */
public class OrderController implements Command{

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");
        if (request.getMethod().equals("GET")){
            if (uri.length == 4 && uri[3].equals("orders"))
                showOrders(request,response);
            else if (uri.length == 5 && uri[4].equals("add"))
                showFormForOrderAdd(request,response);
            else if (uri.length == 6 && uri[5].equals("truck"))
                showFormForTruckAssign(request,response);
            else if (uri.length == 6 && uri[5].equals("driver"))
                showFormForDriverAssign(request,response);
        }
        if (request.getMethod().equals("POST")){
            if (uri.length == 5 && uri[4].equals("add"))
                addOrder(request,response);
            else if (uri.length == 6 && uri[5].equals("truck"))
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
    public void addOrder(HttpServletRequest req, HttpServletResponse resp){

    }

    // /employee/order/{number}/truck GET
    public void showFormForTruckAssign(HttpServletRequest req, HttpServletResponse resp){

    }
    // /employee/order/{number}/truck POST
    public void assignTruck(HttpServletRequest req, HttpServletResponse resp){

    }

    // /employee/order/{number}/driver GET
    public void showFormForDriverAssign(HttpServletRequest req, HttpServletResponse resp){

    }
    // /employee/order/{number}/driver POST
    public void assignDriver(HttpServletRequest req, HttpServletResponse resp){

    }

}
