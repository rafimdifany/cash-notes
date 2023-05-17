package com.minipro.cashnotes.validator.constraint;

import com.minipro.cashnotes.validator.UsernameUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUniqueConstraint {

    String message() default "Username is already taken.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
