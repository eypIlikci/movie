package com.movie.validation;

import com.movie.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateUserEmailValidator implements ConstraintValidator<CreateUserMail,String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository.findByEmail(email).orElse(null)==null)
            return true;
        return false;
    }
}
