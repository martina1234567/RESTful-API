package com.example.car_managment.service;

import com.example.car_managment.dto.CreateMaintenanceDto;
import com.example.car_managment.dto.MaintenanceDto;
import com.example.car_managment.entity.Car;
import com.example.car_managment.entity.Garage;
import com.example.car_managment.entity.Maintenance;
import com.example.car_managment.repository.CarRepository;
import com.example.car_managment.repository.GarageRepository;
import com.example.car_managment.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.car_managment.exception.ResourceNotFoundException;
import com.example.car_managment.exception.GarageFullException;
import java.time.LocalDate;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final GarageRepository garageRepository;
    private final CarRepository carRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository,
                              GarageRepository garageRepository,
                              CarRepository carRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.garageRepository = garageRepository;
        this.carRepository = carRepository;
    }

    @Transactional
    public MaintenanceDto createMaintenance(CreateMaintenanceDto dto) {
        // Validate garage existence
        Garage garage = garageRepository.findById(dto.getGarageId())
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + dto.getGarageId()));

        // Validate car existence
        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + dto.getCarId()));

        // Check garage availability for the date
        boolean isGarageFull = maintenanceRepository.countByGarageIdAndScheduledDate(dto.getGarageId(), dto.getScheduledDate()) >= garage.getCapacity();
        if (isGarageFull) {
            throw new GarageFullException("No available slots in the garage on " + dto.getScheduledDate());
        }

        // Create new maintenance record
        Maintenance maintenance = new Maintenance();
        maintenance.setGarage(garage);
        maintenance.setCar(car);
        maintenance.setServiceType(dto.getServiceType());
        maintenance.setScheduledDate(dto.getScheduledDate());

        maintenance = maintenanceRepository.save(maintenance);

        // Map and return the DTO
        return mapToDto(maintenance);
    }

    private MaintenanceDto mapToDto(Maintenance maintenance) {
        MaintenanceDto dto = new MaintenanceDto();
        dto.setId(maintenance.getId());
        dto.setCarId(maintenance.getCar().getId());
        dto.setCarName(maintenance.getCar().getMake());
        dto.setServiceType(maintenance.getServiceType());
        dto.setScheduledDate(maintenance.getScheduledDate());
        dto.setGarageId(maintenance.getGarage().getId());
        dto.setGarageName(maintenance.getGarage().getName());
        return dto;
    }
}