package com.movie.service;

import com.movie.entity.Token;
import java.util.List;

import com.movie.entity.enums.TokenType;
import com.movie.error.ExpiredTokenException;
import com.movie.error.WrongTokenException;
import com.movie.repo.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {
    private TokenRepository tokenRepository;
    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    public Token findToken(String t){
        Token token=tokenRepository.findByToken(t).orElseThrow(
                ()->new WrongTokenException("Wrong Token")
        );
        if (token.getExDate().isBefore(LocalDateTime.now())){
            tokenRepository.delete(token);
            throw new ExpiredTokenException("Expired Token");
        }
        return token;
    }
    public void save(Token token){
        tokenRepository.save(token);
    }
    public void deleteByToken(Token token){
        tokenRepository.delete(token);
    }
    public List<Token> getAllByRegisterToken(){
        return tokenRepository.getByTokenType(TokenType.REGISTER);
    }
    public List<Token> getAllByPasswordToken(){
        return tokenRepository.getByTokenType(TokenType.PASSWORD);

    }
}
