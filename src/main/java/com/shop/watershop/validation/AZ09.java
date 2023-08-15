package com.shop.watershop.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AZ09Validator.class)
public @interface AZ09 {


    String message() default "Use only 0-9 and a-z";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}