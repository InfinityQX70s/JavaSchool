package com.jschool;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;

/**
 * Created by infinity on 20.02.16.
 */
public class Validator {

    private static final String TRUCK_PATTERN = "[a-zA-Z]{2}\\d{5}";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void validateTruck(String number, String capacity, String shiftSize, String status) throws ControllerException {
        if (!number.matches(TRUCK_PATTERN)){
            throw new ControllerException("Truck number format exception", ControllerStatusCode.VALIDATE);
        }
        if (capacity.length() > 2)
            throw new ControllerException("Truck capacity unbelievable", ControllerStatusCode.VALIDATE);
        try {
            Integer.parseInt(capacity);
        }catch (NumberFormatException e){
            throw new ControllerException("Truck capacity format exception", ControllerStatusCode.VALIDATE);
        }
        if (shiftSize.length() > 1)
            throw new ControllerException("Truck shift size unbelievable", ControllerStatusCode.VALIDATE);
        try {
            Integer.parseInt(shiftSize);
        }catch (NumberFormatException e){
            throw new ControllerException("Truck shift size format exception", ControllerStatusCode.VALIDATE);
        }
        if (!status.equals("ok") && !status.equals("broken")){
            throw new ControllerException("Truck status format exception", ControllerStatusCode.VALIDATE);
        }
    }
    

    public void validateEmail(String email) throws ControllerException {
        if (!email.matches(EMAIL_PATTERN)){
            throw new ControllerException("User email format exception", ControllerStatusCode.VALIDATE);
        }
    }

    public void validateDriverNumber(String number) throws ControllerException {
        try {
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            throw new ControllerException("Driver number format exception", ControllerStatusCode.VALIDATE);
        }
    }
}
