package com.jschool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by infinity on 13.02.16.
 */
public class CustomFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/font") || path.startsWith("/image") || path.startsWith("/WEB-INF/pages")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("/logiweb" + path).forward(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }
}
