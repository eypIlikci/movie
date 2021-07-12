package com.movie.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUpdateFieldValidator.class)
@Documented
public @interface UniqueUpdateField {
    String message() default "Kayıt edilmiş değer!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    ClassName className() default ClassName.ACTOR;
    long id();
}
