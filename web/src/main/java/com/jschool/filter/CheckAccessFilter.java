package com.jschool.filter;

import com.jschool.AppContext;
import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by infinity on 17.02.16.
 */
public class CheckAccessFilter implements Filter {

    private static final String regExpEmployee = ".*/employee/.*";
    private static final String regExpDriver = "/logiweb/driv.*";
    private static final String regExpLog = "/logiweb/log.*";
    private static final String employeeRole = "employee";
    private static final String driverRole = "driver";
    private static final String employeeRedirect = "/employee/orders";
    private static final String driverRedirect = "/driver";
    private static final String rootPath = "/logiweb/";
    private static final String loginPath = "/login";

    private AppContext appContext = AppContext.getInstance();
    private Properties errorProperties = appContext.getErrorProperties();

    private static final Logger LOG = Logger.getLogger(CheckAccessFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        req.setCharacterEncoding("UTF-8");
        String role = (String) req.getSession().getAttribute("role");
        //check if login user and try to access root path redirect on work exist path
        if (role!= null && role.equals(employeeRole) && path.equals(rootPath))
            resp.sendRedirect(employeeRedirect);
        else if (role!= null && role.equals(driverRole) && path.equals(rootPath))
            resp.sendRedirect(driverRedirect);
        //check if not login redirect on login page
        else if (role == null && !path.matches(regExpLog))
            resp.sendRedirect(loginPath);
        //check if login and role equals path can get access to needed page
        else if ((path.matches(regExpEmployee) && role.equals(employeeRole))
                || (path.matches(regExpDriver) && role.equals(driverRole))
                || path.matches(regExpLog))
            chain.doFilter(request, response);
        else
            try {
                throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            } catch (ControllerException e) {
                LOG.warn(e.getMessage());
                req.setAttribute("errorMessage", errorProperties.getProperty(e.getStatusCode().name()));
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            }
    }

    @Override
    public void destroy() {

    }
}
