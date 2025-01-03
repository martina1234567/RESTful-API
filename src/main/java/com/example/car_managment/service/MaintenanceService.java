package com.example.car_managment.service;

import com.example.car_managment.dto.*;
import com.example.car_managment.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.car_managment.entity.Maintenance;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public List<MaintenanceDto> getAllMaintenance() {
        List<Maintenance> maintenance = maintenanceRepository.findAll();
        return maintenance.stream().map(this::mapToDto).toList();
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
    public boolean deleteMaintenance(Long id) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isPresent()) {
            maintenanceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public MaintenanceDto editMaintenance(Long maintenanceId, CreateMaintenanceDto dto) {
        Maintenance existingMaintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found with id: " + maintenanceId));

        validateGarageAvailability(dto.getGarageId(), dto.getScheduledDate());

        Maintenance updatedMaintenance = mapToEntity(dto, existingMaintenance);
        maintenanceRepository.save(updatedMaintenance);

        // Reload the updated data to ensure freshness
        Maintenance reloadedMaintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance record not found after update with id: " + maintenanceId));

        return mapToDto(reloadedMaintenance);
    }


    private void validateGarageAvailability(Long garageId, LocalDate scheduledDate) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + garageId));

        boolean isGarageFull = maintenanceRepository.countByGarageIdAndScheduledDate(garageId, scheduledDate) >= garage.getCapacity();
        if (isGarageFull) {
            throw new GarageFullException("No available slots in the garage on " + scheduledDate);
        }
    }

    private Maintenance mapToEntity(CreateMaintenanceDto dto, Maintenance maintenance) {
        Garage garage = garageRepository.findById(dto.getGarageId())
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + dto.getGarageId()));

        Car car = carRepository.findById(dto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + dto.getCarId()));

        maintenance.setGarage(garage);
        maintenance.setCar(car);
        maintenance.setServiceType(dto.getServiceType());
        maintenance.setScheduledDate(dto.getScheduledDate());
        return maintenance;
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

    public List<MaintenanceDto> getMaintenanceFiltered(Long carId, Long garageId, LocalDate startDate, LocalDate endDate) {
        List<Maintenance> maintenances;

        if (carId != null && garageId != null && startDate != null && endDate != null) {
            maintenances = maintenanceRepository.findByCarIdAndGarageIdAndScheduledDateBetween(carId, garageId, startDate, endDate);
        } else if (carId != null) {
            maintenances = maintenanceRepository.findByCar_Id(carId);
        } else if (garageId != null) {
            maintenances = maintenanceRepository.findByGarage_Id(garageId);
        } else if (startDate != null && endDate != null) {
            maintenances = maintenanceRepository.findByScheduledDateBetween(startDate, endDate);
        } else {
            maintenances = maintenanceRepository.findAll();
        }

        return maintenances.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<MonthlyRequestsReportDto> getMonthlyRequestsSimpleReport(Long garageId, YearMonth startMonth, YearMonth endMonth) {
        List<MonthlyRequestsReportDto> report = new ArrayList<>();

        YearMonth currentMonth = startMonth;
        while (!currentMonth.isAfter(endMonth)) {
            LocalDate startDate = currentMonth.atDay(1);
            LocalDate endDate = currentMonth.atEndOfMonth();

            long requestsCount = maintenanceRepository.countByGarageIdAndDateRange(garageId, startDate, endDate);

            // Create YearMonthDto
            YearMonthDto yearMonthDto = new YearMonthDto(
                    currentMonth.getYear(),
                    currentMonth.getMonth().name(),
                    currentMonth.isLeapYear(),
                    currentMonth.getMonthValue()
            );

            // Add to the report
            report.add(new MonthlyRequestsReportDto(yearMonthDto, requestsCount));

            currentMonth = currentMonth.plusMonths(1);
        }

        return report;
    }

}

