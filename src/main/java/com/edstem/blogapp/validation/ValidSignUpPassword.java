package com.edstem.blogapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface ValidSignUpPassword {
    String message() default "Password must contain 8 characters ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
