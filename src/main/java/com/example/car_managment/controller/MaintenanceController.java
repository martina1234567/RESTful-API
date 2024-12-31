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
    }



