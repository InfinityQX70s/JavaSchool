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
            throw new ControllerException("Some of citie's fields are empty", ControllerStatusCode.EMPTY_FIELDS);
        }
    }


    public void validateOrderAndCargo(String orderNumber, String[] cargoNumber, String[] cargoName, String[] cargoWeight, String[] pickup, String[] unload) throws ControllerException {
        try {
            Integer.parseInt(orderNumber);
        }catch (NumberFormatException e){
            throw new ControllerException("Order number format exception", ControllerStatusCode.ORDER_NUMBER_FORMAT);
        }
        int size = cargoName.length;
        if (cargoNumber.length!=size || cargoWeight.length!=size || pickup.length!=size || unload.length!=size){
            throw new ControllerException("Some of cargo's fields are empty exception", ControllerStatusCode.EMPTY_FIELDS);
        }
        for (String number : cargoNumber){
            try {
                Integer.parseInt(number);
            }catch (NumberFormatException e){
                throw new ControllerException("Cargo number format exception", ControllerStatusCode.CARGO_NUMBER_FORMAT);
            }
        }
        for (String weight : cargoWeight){
            try {
                Integer.parseInt(weight);
            }catch (NumberFormatException e){
                throw new ControllerException("Cargo weight format exception", ControllerStatusCode.CARGO_WEIGHT_FORMAT);
            }
        }
    }

    public void validateTruckDriversAndDuration(String number, String[] driverNumbers, String duration) throws ControllerException {
        for (String driverNumber : driverNumbers){
            try {
                Integer.parseInt(driverNumber);
            }catch (NumberFormatException e){
                throw new ControllerException("Driver number format exception", ControllerStatusCode.DRIVER_NUMBER_FORMAT);
            }
        }
        try {
            Integer.parseInt(duration);
        }catch (NumberFormatException e){
            throw new ControllerException("Duration format exception", ControllerStatusCode.DURATION_FORMAT);
        }
        validateTruckNumber(number);
    }

    public void validateTruckNumber(String number) throws ControllerException {
        if (!number.matches(TRUCK_PATTERN)){
            throw new ControllerException("Truck number format exception", ControllerStatusCode.TRUCK_NUMBER_FORMAT);
        }
    }

    public void validateTruck(String number, String capacity, String shiftSize, String status) throws ControllerException {
        validateTruckNumber(number);
        try {
            Integer.parseInt(capacity);
        }catch (NumberFormatException e){
            throw new ControllerException("Truck capacity format exception", ControllerStatusCode.TRUCK_CAPACITY_FORMAT);
        }
        try {
            Integer.parseInt(shiftSize);
        }catch (NumberFormatException e){
            throw new ControllerException("Truck shift size format exception", ControllerStatusCode.TRUCK_SHIFT_FORMAT);
        }
    }


    public void validateEmail(String email) throws ControllerException {
        if (!email.matches(EMAIL_PATTERN)){
            throw new ControllerException("User email format exception", ControllerStatusCode.EMAIL_FORMAT);
        }
    }

    public void validateDriverNumber(String number) throws ControllerException {
        try {
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            throw new ControllerException("Driver number format exception", ControllerStatusCode.DRIVER_NUMBER_FORMAT);
        }
    }

    public void validateFirstAndLastName(String firstName, String lastName) throws ControllerException {
        if (firstName.isEmpty() || lastName.isEmpty()){
            throw new ControllerException("Empty fields exception", ControllerStatusCode.EMPTY_FIELDS);
        }
    }
}
