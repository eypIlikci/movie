package com.movie.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(){
    }
    public  ExpiredTokenException(String message){
        super(message);
    }
}
