package com.example.car_managment.service;

import com.example.car_managment.dto.CarDto;
import com.example.car_managment.dto.GarageDto;
import com.example.car_managment.entity.Car;
import com.example.car_managment.entity.Garage;
import com.example.car_managment.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private static final Logger logger = Logger.getLogger(CarService.class.getName());

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Method to fetch all cars from the database
    public List<CarDto> getAllCars() {
        // Fetch all cars from the database using the repository
        List<Car> cars = carRepository.findAll();

        // Log the fetched cars for debugging
        logger.info("Fetched Cars: " + cars);

        // Convert the list of Car entities to a list of CarDto objects
        return cars.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert Car entity to CarDto
    private CarDto convertToDTO(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setMake(car.getMake());
        carDto.setModel(car.getModel());
        carDto.setProductionYear(car.getProductionYear());
        carDto.setLicensePlate(car.getLicensePlate());

        // Convert the list of garages to GarageDto objects
        List<GarageDto> garageDtos = car.getGarages().stream()
                .map(this::convertGarageToDto)
                .collect(Collectors.toList());

        carDto.setGarages(garageDtos);
        return carDto;
    }

    // Helper method to convert Garage entity to GarageDto
    private GarageDto convertGarageToDto(Garage garage) {
        GarageDto garageDto = new GarageDto();
        garageDto.setId(garage.getId());
        garageDto.setName(garage.getName());
        garageDto.setLocation(garage.getLocation());
        garageDto.setCity(garage.getCity());
        garageDto.setCapacity(garage.getCapacity());
        return garageDto;
    }
}
