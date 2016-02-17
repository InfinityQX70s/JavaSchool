package com.jschool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 13.02.16.
 */
public class CustomFilter implements Filter {

    private static String regExp = "/(css|js|font|image|WEB-INF).*";

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.matches(regExp)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("/logiweb" + path).forward(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }
}
