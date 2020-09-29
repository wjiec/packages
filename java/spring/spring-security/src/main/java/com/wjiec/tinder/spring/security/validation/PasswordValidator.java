package com.wjiec.tinder.spring.security.validation;

import com.wjiec.tinder.spring.security.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidMatchedPassword, UserDTO> {

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getConfirmPassword());
    }

}
