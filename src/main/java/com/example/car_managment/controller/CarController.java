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

//    @GetMapping
//    public ResponseEntity<List<CarDto>> getCarsByMake(@RequestParam(required = false) String carMake) {
//        if (carMake != null) {
//            return ResponseEntity.ok(carService.getCarsByMake(carMake));
//        }
//        return ResponseEntity.ok(carService.getAllCars());
//    }
@GetMapping
public ResponseEntity<List<CarDto>> getCars(
        @RequestParam(required = false) String carMake,
        @RequestParam(required = false) Long garageId,
        @RequestParam(required = false) Integer fromYear,
        @RequestParam(required = false) Integer toYear) {

    if (carMake != null) {
        return ResponseEntity.ok(carService.getCarsByMake(carMake));
    }

    if (garageId != null) {
        return ResponseEntity.ok(carService.getCarsByGarage(garageId));
    }

    if (fromYear != null || toYear != null) {
        return ResponseEntity.ok(carService.getCarsByYearRange(fromYear, toYear));
    }

    return ResponseEntity.ok(carService.getAllCars());
}




    @PostMapping
    public ResponseEntity<CarDto> addCar(@RequestBody CarDto carDto) {
        return ResponseEntity.ok(carService.addCar(carDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @RequestBody CarDto carDto) {
        return ResponseEntity.ok(carService.updateCar(id, carDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
