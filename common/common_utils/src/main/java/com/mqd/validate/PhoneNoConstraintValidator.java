package com.mqd.validate;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNoConstraintValidator implements ConstraintValidator<PhoneNoConstraint,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String regex = "1\\d{10}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(value);
        return matcher.matches();
    }
}
