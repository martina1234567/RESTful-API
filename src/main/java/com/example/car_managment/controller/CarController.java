package com.example.car_managment.controller;

import com.example.car_managment.dto.CarDto;
import com.example.car_managment.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private static final Logger logger = Logger.getLogger(CarController.class.getName());

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        // Log the request for debugging
        logger.info("Fetching all cars...");

        // Call service to get all cars and return the response
        List<CarDto> cars = carService.getAllCars();

        // Log the response data for debugging
        logger.info("Cars fetched: " + cars);

        return ResponseEntity.ok(cars);
    }
}
