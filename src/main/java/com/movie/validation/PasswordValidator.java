package com.movie.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password,String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if ( password==null)return false;
        if (checkLowercase(password)
                && checkUppercase(password)
                && checkInt(password))return true;
        return false;
    }

    private boolean checkUppercase(String pass){
        for(char p:pass.toCharArray()){
            if ((int)p>=65 && (int)p<=90)return true;
        }
        return false;
    }
    private boolean checkLowercase(String pass){
        for(char p:pass.toCharArray()){
            if ((int)p>=97 && (int)p<=122)return true;
        }
        return false;
    }
    private boolean checkInt(String pass){
        for(char p:pass.toCharArray()){
            if ((int)p>=48 && (int)p<=57)return true;
        }
        return false;
    }
}
