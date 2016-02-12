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

    // /manager/order/
    public void showOrders(HttpServletRequest req, HttpServletResponse resp){

    }

   // /manager/order/add GET
    public void showFormForOrderAdd(HttpServletRequest req, HttpServletResponse resp){

    }
 //   /manager/order/add POST
    public void addOrder(HttpServletRequest req, HttpServletResponse resp){

    }

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
