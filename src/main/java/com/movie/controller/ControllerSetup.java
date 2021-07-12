package com.movie.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ControllerSetup {
    @InitBinder
    public void init(WebDataBinder binder){
        StringTrimmerEditor trimmerEditor=new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class,trimmerEditor);
    }
}
