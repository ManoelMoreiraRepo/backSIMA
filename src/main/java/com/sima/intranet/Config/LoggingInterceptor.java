package com.sima.intranet.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("Request URL: " + request.getRequestURL());
        return true; // Si devuelves false, la ejecución del controlador se detendrá.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("Request URL: " + request.getRequestURL());
        // Este método se llama después de que el controlador haya manejado la solicitud.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("Request URL: " + request.getRequestURL());
        // Este método se llama después de que la vista se haya renderizado.
    }
}

