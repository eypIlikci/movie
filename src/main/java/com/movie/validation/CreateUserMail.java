package com.movie.validation;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreateUserEmailValidator.class)
@Documented
public @interface CreateUserMail {

    String message() default "Email benzersiz olmalı!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
