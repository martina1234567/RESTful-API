package com.example.car_managment.controller;

import com.example.car_managment.dto.CreateMaintenanceDto;
import com.example.car_managment.dto.GarageDto;
import com.example.car_managment.dto.MaintenanceDto;
import com.example.car_managment.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }




    @PostMapping
    public ResponseEntity<MaintenanceDto> createMaintenance(@RequestBody CreateMaintenanceDto createMaintenanceDto) {
        MaintenanceDto created = maintenanceService.createMaintenance(createMaintenanceDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDto> editMaintenance(
            @PathVariable Long id,
            @RequestBody CreateMaintenanceDto dto) {
        MaintenanceDto updatedMaintenance = maintenanceService.editMaintenance(id, dto);

        if (updatedMaintenance != null) {
            return ResponseEntity.ok(updatedMaintenance);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // GET /maintenance
    @GetMapping
    public ResponseEntity<List<MaintenanceDto>> getAllMaintenance(
            @RequestParam(required = false) Long carId,
            @RequestParam(required = false) Long garageId,
            @RequestParam(required = false) String scheduledDate) {

        // Add filtering logic if necessary
//        if (carId != null) {
//            return ResponseEntity.ok(maintenanceService.getMaintenanceByCarId(carId));
//        }
//        if (garageId != null) {
//            return ResponseEntity.ok(maintenanceService.getMaintenanceByGarageId(garageId));
//        }
//        if (scheduledDate != null) {
//            return ResponseEntity.ok(maintenanceService.getMaintenanceByScheduledDate(scheduledDate));
//        }

        return ResponseEntity.ok(maintenanceService.getAllMaintenance());
    }



}



