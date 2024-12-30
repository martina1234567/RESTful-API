package com.example.car_managment.controller;

import com.example.car_managment.dto.GarageDto;
import com.example.car_managment.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garages")
public class GarageController {

    @Autowired
    private GarageService garageService;

    // GET /garages/{id}
    @GetMapping("/{id}")
    public ResponseEntity<GarageDto> getGarageById(@PathVariable Long id) {
        GarageDto garageDto = garageService.getGarageById(id);
        if (garageDto != null) {
            return ResponseEntity.ok(garageDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // GET /garages
    @GetMapping
    public ResponseEntity<List<GarageDto>> getAllGarages(@RequestParam(required = false) String city) {
        if (city != null) {
            return ResponseEntity.ok(garageService.getGaragesByCity(city));
        }
        return ResponseEntity.ok(garageService.getAllGarages());
    }

    // POST /garages
    @PostMapping
    public ResponseEntity<GarageDto> createGarage(@RequestBody GarageDto garageDto) {
        GarageDto createdGarage = garageService.createGarage(garageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGarage);
    }

    // PUT /garages/{id}
    @PutMapping("/{id}")
    public ResponseEntity<GarageDto> updateGarage(@PathVariable Long id, @RequestBody GarageDto garageDto) {
        GarageDto updatedGarage = garageService.updateGarage(id, garageDto);
        if (updatedGarage != null) {
            return ResponseEntity.ok(updatedGarage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE /garages/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        if (garageService.deleteGarage(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}