package com.minipro.cashnotes.validator;

import com.minipro.cashnotes.entity.Users;
import com.minipro.cashnotes.repository.UserRepository;
import com.minipro.cashnotes.validator.constraint.UsernameIsUniqueValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UsernameIsUniqueValidator implements ConstraintValidator<UsernameIsUniqueValidation, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(UsernameIsUniqueValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Users user = userRepository.findByUsername(value);

        if(Objects.isNull(user)) {
            return true;
        }
        return false;
    }

}
