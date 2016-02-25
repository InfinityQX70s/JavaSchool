package com.jschool;

import com.jschool.controllers.exception.ControllerException;
import com.jschool.controllers.exception.ControllerStatusCode;
import com.sun.javafx.binding.BidirectionalBinding;

/**
 * Created by infinity on 20.02.16.
 */
public class Validator {

    private static final String TRUCK_PATTERN = "[a-zA-Z]{2}\\d{5}";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void validateCargoCities(String[] pickup, String[] unload) throws ControllerException {
        if (pickup.length!=unload.length){
            throw new ControllerException("Some of catie's fields are empty", ControllerStatusCode.VALIDATE);
        }
    }


    public void validateOrderAndCargo(String orderNumber, String[] cargoNumber, String[] cargoName, String[] cargoWeight, String[] pickup, String[] unload) throws ControllerException {
        try {
            Integer.parseInt(orderNumber);
        }catch (NumberFormatException e){
            throw new ControllerException("Order number format exception", ControllerStatusCode.VALIDATE);
        }
        int size = cargoName.length;
        if (cargoNumber.length!=size || cargoWeight.length!=size || pickup.length!=size || unload.length!=size){
            throw new ControllerException("Some of cargo's fields are empty exception", ControllerStatusCode.VALIDATE);
        }
        for (String number : cargoNumber){
            try {
                Integer.parseInt(number);
            }catch (NumberFormatException e){
                throw new ControllerException("Cargo number format exception", ControllerStatusCode.VALIDATE);
            }
        }
        for (String weight : cargoWeight){
            try {
                Integer.parseInt(weight);
            }catch (NumberFormatException e){
                throw new ControllerException("Cargo weight format exception", ControllerStatusCode.VALIDATE);
            }
        }
    }

    public void validateTruckAndDrivers(String number, String[] driverNumbers) throws ControllerException {
        for (String driverNumber : driverNumbers){
            try {
                Integer.parseInt(driverNumber);
            }catch (NumberFormatException e){
                throw new ControllerException("Driver number format exception", ControllerStatusCode.VALIDATE);
            }
        }
        validateTruckNumber(number);
    }

    public void validateTruckNumber(String number) throws ControllerException {
        if (!number.matches(TRUCK_PATTERN)){
            throw new ControllerException("Truck number format exception", ControllerStatusCode.VALIDATE);
        }
    }

    public void validateTruck(String number, String capacity, String shiftSize, String status) throws ControllerException {
        validateTruckNumber(number);
        try {
            Integer.parseInt(capacity);
        }catch (NumberFormatException e){
            throw new ControllerException("Truck capacity format exception", ControllerStatusCode.VALIDATE);
        }
        if (capacity.length() > 2)
            throw new ControllerException("Truck capacity unbelievable", ControllerStatusCode.VALIDATE);
        try {
            Integer.parseInt(shiftSize);
        }catch (NumberFormatException e){
            throw new ControllerException("Truck shift size format exception", ControllerStatusCode.VALIDATE);
        }
        if (shiftSize.length() > 1)
            throw new ControllerException("Truck shift size unbelievable", ControllerStatusCode.VALIDATE);

        if (!"ok".equals(status) && !"broken".equals(status)){
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

    public void validateFirstAndLastName(String firstName, String lastName) throws ControllerException {
        if (firstName.isEmpty() || lastName.isEmpty()){
            throw new ControllerException("Empty fields exception", ControllerStatusCode.VALIDATE);
        }
    }
}
