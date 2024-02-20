package com.demo.marbgroup.configs;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter  {
    static final String ORIGIN = "Origin";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getHeader(ORIGIN) != null) {
            String origin = request.getHeader(ORIGIN);
            response.setHeader("Access-Control-Allow-Origin", origin);//* or origin as u prefer
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, HEAD");
            
           response.setHeader("Access-Control-Allow-Headers",
                    request.getHeader("Access-Control-Request-Headers"));
        }
        if (request.getMethod().equals("OPTIONS")) {
            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
        filterChain.doFilter(request, response);
        }
    }
}