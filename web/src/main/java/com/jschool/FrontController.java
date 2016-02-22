package com.jschool;

import com.jschool.controllers.BaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 12.02.16.
 */
public class FrontController extends HttpServlet {

    private AppContext appContext = AppContext.getInstance();

    protected void processRequest(HttpServletRequest
                                          request, HttpServletResponse response)
            throws ServletException, IOException {
            BaseController controller = appContext.getControllerFactory().getControllerByUri(request.getRequestURI());
            controller.execute(getServletContext(), request, response);
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
