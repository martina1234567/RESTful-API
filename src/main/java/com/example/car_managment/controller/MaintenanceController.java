package com.example.car_managment.controller;

import com.example.car_managment.dto.CreateMaintenanceDto;
import com.example.car_managment.dto.GarageDto;
import com.example.car_managment.dto.MaintenanceDto;
import com.example.car_managment.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
//    @GetMapping
//    public ResponseEntity<List<MaintenanceDto>> getAllMaintenance(
//            @RequestParam(required = false) Long carId,
//            @RequestParam(required = false) Long garageId,
//            @RequestParam(required = false) String scheduledDate) {
//
//
//
//        return ResponseEntity.ok(maintenanceService.getAllMaintenance());
//    }
    @GetMapping
    public ResponseEntity<List<MaintenanceDto>> getAllMaintenance(
            @RequestParam(required = false) Long carId,
            @RequestParam(required = false) Long garageId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<MaintenanceDto> filteredMaintenances = maintenanceService.getMaintenanceFiltered(carId, garageId, startDate, endDate);
        return ResponseEntity.ok(filteredMaintenances);
    }

    // DELETE /garages/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@PathVariable Long id) {
        if (maintenanceService.deleteMaintenance(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}



