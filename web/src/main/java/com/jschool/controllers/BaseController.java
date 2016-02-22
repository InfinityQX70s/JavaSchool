package com.jschool.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 12.02.16.
 */
public interface BaseController {

    void execute(ServletContext servletContext,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
