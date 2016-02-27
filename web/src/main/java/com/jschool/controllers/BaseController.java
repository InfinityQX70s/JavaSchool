package com.jschool.controllers;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.services.api.exception.ServiceException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by infinity on 12.02.16.
 */
public abstract class BaseController {

    private Properties errorProperties;

    public BaseController(Properties errorProperties) {
        this.errorProperties = errorProperties;
    }

    public abstract void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void showError(ControllerException e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorMessage", errorProperties.getProperty(e.getStatusCode().name()));
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }

    protected void showError(ServiceException e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorMessage", errorProperties.getProperty(e.getStatusCode().name()));
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }
}
