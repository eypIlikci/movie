package com.movie.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErrorHandler{

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView notFound(){
        return new ModelAndView("404-page");
    }
    @ExceptionHandler(WrongTokenException.class)
    public ModelAndView wrongToken(){
        ModelAndView modelAndView=new ModelAndView("message-page");
        modelAndView.addObject("message","!!Yanlış Token!!");
        return modelAndView;
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ModelAndView exToken(){
        ModelAndView modelAndView=new ModelAndView("message-page");
        modelAndView.addObject("message","!!Süresi Dolan İşlem!!");
        return modelAndView;
    }


}
