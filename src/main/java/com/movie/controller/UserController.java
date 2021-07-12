package com.movie.controller;

import com.movie.dto.CreateUserRequest;
import com.movie.dto.EmailRequest;
import com.movie.dto.ResetPasswordRequest;
import com.movie.entity.Token;
import com.movie.service.TokenService;
import com.movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;
    private TokenService tokenService;

    @Autowired
    public UserController(UserService userService,
                          TokenService tokenService) {
        this.userService = userService;
        this.tokenService=tokenService;
    }

    @GetMapping("/sing-in")
    public String loginForm(){
        return "sing-in";
    }


    @GetMapping("/sing-up")
    public String registerForm(ModelMap model){
        model.addAttribute("user",new CreateUserRequest());
        return "sing-up";
    }

    @PostMapping("/kayit")
    public ModelAndView register(@Valid @ModelAttribute("user") CreateUserRequest request,
                                 BindingResult bindingResult, Errors errors){
        ModelAndView model=new ModelAndView();

        if (bindingResult.hasErrors()){

            for (FieldError error:bindingResult.getFieldErrors()) {
                model.addObject(error.getField(),error.getDefaultMessage());
            }
            model.setViewName("sing-up");
            model.addObject("user",new CreateUserRequest());
            return model;
        }
        userService.register(request);
        model.addObject("title","Kayıt Onay");
        model.addObject("message","Email adresine gönderilen activasyon linki ile kayıtdını gerçekleştir.");
        model.setViewName("message-page");
        return model;
    }


    @GetMapping("/confirm/{token}")
    public String confirmRegister(@PathVariable("token") String token,
                                  ModelMap model){
        userService.confirmRegister(token);
        model.addAttribute("message","Kayıt Onaylandı");
        return "message-page";
    }

    @GetMapping("/unuttum")
    public String forgotForm(ModelMap modelMap){
        modelMap.addAttribute("email",new EmailRequest());
        return "forgot-form";
    }
    @PostMapping("/unuttum")
    public ModelAndView forgot(@Valid @ModelAttribute("email") EmailRequest request,
                               BindingResult result){
        ModelAndView modelAndView=new ModelAndView();
        if (result.hasErrors()){
            modelAndView.addObject("email",request);
            modelAndView.setViewName("forgot/form");
            result.getFieldErrors().stream().
                    forEach(error -> modelAndView.addObject(error.getField(),error.getDefaultMessage()));
            return modelAndView;
        }
        userService.forgot(request);
        modelAndView.addObject("message","E-Mail addresini kontrol et");
        modelAndView.setViewName("message-page");
        return modelAndView;

    }

    @GetMapping("/forgot/{token}")
    public ModelAndView forgotConfirmForm(@PathVariable("token") String t){
        Token token=tokenService.findToken(t);
        ResetPasswordRequest request=new ResetPasswordRequest();
        request.setEmail(token.getUser().getEmail());
        request.setToken(token.getToken());

        ModelAndView modelAndView=new ModelAndView("reset-password-form");
        modelAndView.addObject("reset",request);
        return modelAndView;
    }

    @PostMapping("/forgot")
    public ModelAndView resetPassword(@Valid @ModelAttribute("reset") ResetPasswordRequest request,
                                      BindingResult result){
        ModelAndView modelAndView=new ModelAndView();
        if (result.hasErrors()){
            modelAndView.addObject("reset",request);
            result.getFieldErrors().stream()
                    .forEach(error -> modelAndView.addObject(error.getField(),error.getDefaultMessage()));
            modelAndView.setViewName("reset-password-form");
            return modelAndView;
        }
        userService.resetPassword(request);
        modelAndView.setViewName("message-page");
        modelAndView.addObject("message","Şifren yenilendin");
        return modelAndView;
    }
}
