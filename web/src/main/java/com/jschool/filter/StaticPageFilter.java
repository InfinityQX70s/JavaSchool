package com.jschool.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 13.02.16.
 */
public class StaticPageFilter implements Filter {

    private static String regExp = "/(css|js|font|image|WEB-INF).*";
    private static final String rootPath = "/logiweb";

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.matches(regExp)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher(rootPath + path).forward(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }
}
