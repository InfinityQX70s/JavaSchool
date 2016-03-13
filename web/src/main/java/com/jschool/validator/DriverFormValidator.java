package com.jschool.validator;

import com.jschool.entities.Driver;
import com.jschool.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by infinity on 07.03.16.
 */
@Component
public class DriverFormValidator implements Validator {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean supports(Class<?> aClass) {
        return Driver.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Driver driver = (Driver)o;
        User user  = driver.getUser();
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.driverForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.driverForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city.name", "NotEmpty.driverForm.city");
        if(driver.getNumber() <= 0){
            errors.rejectValue("number", "NotEmpty.driverForm.number");
        }
        if (!user.getEmail().matches(EMAIL_PATTERN) && user.getEmail().isEmpty()){
            errors.rejectValue("user.email", "NotEmpty.driverForm.email");
        }
        else if (!user.getEmail().matches(EMAIL_PATTERN)){
            errors.rejectValue("user.email", "Pattern.driverForm.email");
        }
    }
}
