package com.jschool.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by infinity on 12.02.16.
 */
public class LoginController implements Command {


    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/WEB-INF/pages/login.jsp");
        requestDispatcher.forward(request, response);
    }
}
