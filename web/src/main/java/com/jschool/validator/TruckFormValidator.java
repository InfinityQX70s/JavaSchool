package com.jschool.validator;

import com.jschool.entities.Truck;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by infinity on 08.03.16.
 */
@Component
public class TruckFormValidator implements Validator {

    private static final String TRUCK_PATTERN = "[a-zA-Z]{2}\\d{5}";

    @Override
    public boolean supports(Class<?> aClass) {
        return Truck.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Truck truck = (Truck)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "NotEmpty.truckForm.number");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capacity", "NotEmpty.truckForm.capacity");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shiftSize", "NotEmpty.truckForm.shiftSize");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city.name", "NotEmpty.truckForm.city");
        if (!truck.getNumber().matches(TRUCK_PATTERN))
            errors.rejectValue("number", "Pattern.truckForm.number");
        if (truck.getCapacity() <= 0 || truck.getCapacity() > 70)
            errors.rejectValue("capacity", "Pattern.truckForm.capacity");
        if (truck.getShiftSize() <= 0 || truck.getShiftSize() > 6 )
            errors.rejectValue("shiftSize", "Pattern.truckForm.shiftSize");
    }
}
