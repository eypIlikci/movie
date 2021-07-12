package com.movie.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Filter extends GenericFilter {

    @Autowired
    private Environment environment;
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();

        //Sing-in and Sing-up filter
        if (auth!=null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())
                && (servletRequest.getRequestURI().equals("/sing-in") || (servletRequest.getRequestURI().equals("/sing-up")))){
            String s=servletRequest.getRequestURL().toString()
                    .substring(0,servletRequest.getRequestURL().length()-7);
            servletResponse.sendRedirect(s);

        }else filterChain.doFilter(servletRequest,servletResponse);
    }
}
