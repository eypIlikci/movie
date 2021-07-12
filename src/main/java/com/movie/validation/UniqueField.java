package com.movie.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueFieldValidator.class)
@Documented
public @interface UniqueField {

    String message() default "Kayıt edilmiş değer!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    ClassName className() default ClassName.ACTOR;

}
