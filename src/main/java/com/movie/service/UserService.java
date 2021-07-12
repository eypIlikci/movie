package com.movie.service;

import com.movie.dto.CreateUserRequest;
import com.movie.dto.EmailRequest;
import com.movie.dto.MessageResponse;
import com.movie.dto.ResetPasswordRequest;
import com.movie.entity.Token;
import com.movie.entity.User;
import com.movie.entity.enums.TokenType;
import com.movie.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private TokenService tokenService;
    private MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository,
                       TokenService tokenService,
                       MailService mailService) {
        this.userRepository = userRepository;
        this.tokenService=tokenService;
        this.mailService=mailService;
    }

    public MessageResponse register(CreateUserRequest request){
        //TODO: Register User
        User user=request.getUser();
        user.setLocked(true);
        user=userRepository.save(user);

        //TODO: Create Token
        Token token=getRegisterToken(user);
        tokenService.save(token);

        //TODO:Send Mail
        mailService.sendRegisterMail(user.getEmail(),user.getName(),token.getToken());

        return new MessageResponse("Send Mail");
    }

    public MessageResponse confirmRegister(String t){
        //TODO: Check Token
        Token token=tokenService.findToken(t);

        //TODO: Update User
        User user=token.getUser();
        user.setLocked(false);

        userRepository.save(user);
        //TODO: Delete Token
        tokenService.deleteByToken(token);

        return new MessageResponse("User confirmed");
    }

    public MessageResponse resetPassword(ResetPasswordRequest request){
        Token token=tokenService.findToken(request.getToken());
        User user=token.getUser();
        user.setPassword(request.getPassword());
        userRepository.save(user);
        tokenService.deleteByToken(token);
        return new MessageResponse("Password renewed");
    }

    public MessageResponse forgot(EmailRequest request){
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()->new IllegalStateException()
        );
        //TODO:Create Token
       Token token=getPasswordToken(user);
       tokenService.save(token);

       //TODO:Send Mail
        mailService.sendPasswordMail(user.getEmail(),user.getName(),token.getToken());

        return new MessageResponse("Send Mail");
    }

    public void delete(Long  id){
        User user=userRepository.findById(id).orElse(null);
        if (user!=null)
            userRepository.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email).orElse(null);
        if (user==null)
            throw new UsernameNotFoundException("User not found!");
        return user;
    }

    private Token getRegisterToken(User user){
        Token token=new Token();
        token.setToken(getTokenString());
        token.setTokenType(TokenType.REGISTER);
        token.setUser(user);
        token.setCreateDate(LocalDateTime.now());
        token.setExDate(LocalDateTime.now().plusMinutes(120));
        return token;
    }

    private Token getPasswordToken(User user){
        Token token=new Token();
        token.setToken(getTokenString());
        token.setTokenType(TokenType.PASSWORD);
        token.setUser(user);
        token.setCreateDate(LocalDateTime.now());
        token.setExDate(LocalDateTime.now().plusMinutes(30));
        return token;
    }


    private String getTokenString(){
        return UUID.randomUUID().toString();
    }


}
