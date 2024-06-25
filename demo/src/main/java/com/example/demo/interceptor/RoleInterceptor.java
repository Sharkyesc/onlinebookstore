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
        System.out.print(uri);
        System.out.println(" + " + username);

        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please login first");
            System.out.print(1);
            return false;
        }

        if (uri.startsWith("/admin") && !username.equals("admin")) {
            System.out.print(2);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "not allowed to view this page");
            return false;
        }

        System.out.print(3);

        return true;
    }

}
