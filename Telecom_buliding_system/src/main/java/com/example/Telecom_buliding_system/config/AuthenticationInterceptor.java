package com.example.Telecom_buliding_system.config;

import com.example.Telecom_buliding_system.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Skip authentication for login page and public resources
        String path = request.getRequestURI();
        if (path.equals("/login") || path.equals("/register") || path.contains("/css/") ||
                path.contains("/js/") || path.contains("/images/") || path.contains("/refresh-token")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return false;
        }

        String token = (String) session.getAttribute("jwtToken");
        if (token == null || !JwtUtil.isTokenValid(token)) {
            session.invalidate();
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // You can add additional logic after handler execution if needed
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        // You can add cleanup logic after complete request processing
    }
}