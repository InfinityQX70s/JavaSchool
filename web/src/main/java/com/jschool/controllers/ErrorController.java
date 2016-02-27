package com.jschool.controllers;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by infinity on 22.02.16.
 */
public class ErrorController extends BaseController {

    private static final Logger LOG = Logger.getLogger(ErrorController.class);

    public ErrorController(Properties errorProperties) {
        super(errorProperties);
    }

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }
}
