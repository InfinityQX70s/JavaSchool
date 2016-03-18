package com.jschool.rest;

/**
 * Created by infinity on 12.03.16.
 */
public class JsonResponse {

    private String status;
    private String result;
    private Integer cargoNumber;
    private String cargoName;
    private String city;
    private String type;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public Integer getCargoNumber() {
        return cargoNumber;
    }

    public void setCargoNumber(Integer cargoNumber) {
        this.cargoNumber = cargoNumber;
    }
}
