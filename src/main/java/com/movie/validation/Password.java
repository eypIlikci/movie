package com.movie.validation;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "Şifre en az, 1 küçük harf, 1 büyük harf ve 1 rakam içermeli!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
