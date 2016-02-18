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
        req.setCharacterEncoding("UTF-8");
        String role = (String) req.getSession().getAttribute("role");
        if (role!= null && role.equals("employee") && path.equals("/logiweb/"))
            resp.sendRedirect("/employee/orders");
        else if (role!= null && role.equals("driver") && path.equals("/logiweb/"))
            resp.sendRedirect("/driver");
        else if (role == null && !path.matches(regExpLog))
            resp.sendRedirect("/login");
        else if ((path.matches(regExpEmployee) && role.equals("employee"))
                || (path.matches(regExpDriver) && role.equals("driver"))
                || path.matches(regExpLog))
            chain.doFilter(request, response);
        else
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {

    }
}
