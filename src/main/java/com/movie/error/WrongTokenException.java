package com.movie.error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongTokenException extends RuntimeException {
    public WrongTokenException(){
    }
    public  WrongTokenException(String message){
        super(message);
    }
}
