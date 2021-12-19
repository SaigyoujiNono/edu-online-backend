package com.mqd.validate;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNoConstraintValidator implements ConstraintValidator<PhoneNoConstraint,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0){
            return true;
        }
        String regex = "0?(13|14|15|17|18|19)[0-9]{9}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(value);
        return matcher.matches();
    }
}
