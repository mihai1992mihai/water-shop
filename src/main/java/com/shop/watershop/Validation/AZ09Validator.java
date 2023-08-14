package com.shop.watershop.Validation;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AZ09Validator implements ConstraintValidator<AZ09, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(value);
        boolean badCharacters = matcher.find(); //false if characters are a-z or 0-9
        if(!badCharacters){
            return true;
        }
        return false;
    }
}
