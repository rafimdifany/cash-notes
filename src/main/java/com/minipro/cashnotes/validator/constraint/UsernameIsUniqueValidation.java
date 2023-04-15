package com.minipro.cashnotes.validator.constraint;

import com.minipro.cashnotes.validator.UsernameIsUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameIsUniqueValidator.class)
public @interface UsernameIsUniqueValidation {

    String message() default "Username is already Taken.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
