package com.movie.security;


import com.movie.entity.User;
import com.movie.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AuthenticatedUser {
    @ModelAttribute("session_user")
    public AuthUser getUser(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            User user=(User) auth.getPrincipal();
            return new AuthUser(user.getName(),user.getEmail(),user.getRole().toString());
        }
        return null;
    }
}
