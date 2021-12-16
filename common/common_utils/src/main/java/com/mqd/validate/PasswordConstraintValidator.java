package com.mqd.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<Password,String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0){
            return true;
        }
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(value);
        return matcher.matches();
    }
}
