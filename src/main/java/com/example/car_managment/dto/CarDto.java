package com.example.car_managment.dto;

import java.util.List;

public class CarDto {
    private Long id;
    private String make;
    private String model;
    private int productionYear;
    private String licensePlate;
    private List<GarageDto> garages;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<GarageDto> getGarages() {
        return garages;
    }

    public void setGarages(List<GarageDto> garages) {
        this.garages = garages;
    }
}
