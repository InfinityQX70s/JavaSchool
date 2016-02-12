package com.jschool;

import com.jschool.controllers.Command;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 12.02.16.
 */
public class FrontController extends HttpServlet {

    protected void processRequest(HttpServletRequest
                                          request, HttpServletResponse response)
            throws ServletException, IOException {
            Command command = CommandFactory.create(request);
            command.execute(getServletContext(), request, response);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
