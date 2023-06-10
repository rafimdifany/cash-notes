package com.minipro.cashnotes.validator;

import com.minipro.cashnotes.service.UserService;
import com.minipro.cashnotes.validator.constraint.UsernameUniqueConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class UsernameUniqueValidator implements ConstraintValidator<UsernameUniqueConstraint, String> {

    @Autowired
    UserService userService;


    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        log.info("UsernameUniqueValidator:: username : {}", username);
        try {
            userService.getUserByUsername(username);
            return false;
        } catch (NotFoundException e) {
            return true;
        }
    }
}
