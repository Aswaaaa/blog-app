package com.edstem.blogapp.validation;

import jakarta.validation.Payload;

public @interface ValidSignUpPassword {
    String message() default "Password must contain 8 characters ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
