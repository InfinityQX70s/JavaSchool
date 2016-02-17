package com.jschool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 17.02.16.
 */
@Deprecated
public class LoginFilter implements Filter {

    private static String regExpEmployee = ".*/employee/.*";
    private static String regExpDriver = "/logiweb/driver/.*";
    private static String regExpLog = "/logiweb/log.*";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        String role = (String) req.getSession().getAttribute("role");
        if (role.equals("employee") && path.equals("/logiweb/"))
            resp.sendRedirect("/employee/orders");
        if (role.equals("driver") && path.equals("/logiweb/"))
            resp.sendRedirect("/driver");
        if (role.isEmpty() && path.equals("/logiweb/"))
            resp.sendRedirect("/login");
        if ((path.matches(regExpEmployee) && role.equals("employee"))
                || (path.matches(regExpDriver) && role.equals("driver"))
                || path.matches(regExpLog)) {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
