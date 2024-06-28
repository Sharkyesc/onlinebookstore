package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String username = (String) request.getSession().getAttribute("user");

        String uri = request.getRequestURI();

        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login first");
            return false;
        }

        if (uri.startsWith("/admin") && !username.equals("admin")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "not allowed to view this page");
            return false;
        }

        return true;
    }

}
