package com.jschool.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 17.02.16.
 */
public class LoginFilter implements Filter {

    private static final String regExpEmployee = ".*/employee/.*";
    private static final String regExpDriver = "/logiweb/driver/.*";
    private static final String regExpLog = "/logiweb/log.*";
    private static final String employeeRole = "employee";
    private static final String driverRole = "driver";
    private static final String employeeRedirect = "/employee/orders";
    private static final String driverRedirect = "/driver";
    private static final String rootPath = "/logiweb/";
    private static final String loginPath = "/login";


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
        if (role!= null && role.equals(employeeRole) && path.equals(rootPath))
            resp.sendRedirect(employeeRedirect);
        else if (role!= null && role.equals(driverRole) && path.equals(rootPath))
            resp.sendRedirect(driverRedirect);
        else if (role == null && !path.matches(regExpLog))
            resp.sendRedirect(loginPath);
        else if ((path.matches(regExpEmployee) && role.equals(employeeRole))
                || (path.matches(regExpDriver) && role.equals(driverRole))
                || path.matches(regExpLog))
            chain.doFilter(request, response);
        else
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {

    }
}
