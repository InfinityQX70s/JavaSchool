package com.jschool.validator;

import com.jschool.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by infinity on 24.03.16.
 */
@Component
public class UserFormValidator implements Validator {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
        if (!user.getEmail().matches(EMAIL_PATTERN) && user.getEmail().isEmpty()){
            errors.rejectValue("email", "NotEmpty.userForm.email");
        }
        else if (!user.getEmail().matches(EMAIL_PATTERN)){
            errors.rejectValue("email", "Pattern.userForm.email");
        }
    }
}
