package tbs.tbsapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidation {

    String message() default "Please enter a valid email address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}