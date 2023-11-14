package com.edstem.blogapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Valid;

public class PasswordValidator implements ConstraintValidator<ValidSignUpPassword,String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        return password.length() >= 8;
    }
}
