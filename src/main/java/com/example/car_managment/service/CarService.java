package com.example.car_managment.service;

import com.example.car_managment.dto.CarDto;
import com.example.car_managment.dto.GarageDto;
import com.example.car_managment.entity.Car;
import com.example.car_managment.entity.Garage;
import com.example.car_managment.repository.CarRepository;
import com.example.car_managment.repository.GarageRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final GarageRepository garageRepository;
    private static final Logger logger = Logger.getLogger(CarService.class.getName());

    public CarService(CarRepository carRepository, GarageRepository garageRepository) {
        this.carRepository = carRepository;
        this.garageRepository = garageRepository;
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

    // Add new car with associated garages
    public CarDto addCar(CarDto carDto) {
        Car car = convertToEntity(carDto);

        // Fetch garages by their IDs and associate them with the car
        List<Garage> garages = garageRepository.findAllById(carDto.getGarageIds());

        // Convert List<Garage> to Set<Garage> if necessary
        car.setGarages(new HashSet<>(garages)); // Преобразувайте в Set

        car = carRepository.save(car);
        return convertToDTO(car);
    }

    // Update car and its associated garages
    public CarDto updateCar(Long id, CarDto carDto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setProductionYear(carDto.getProductionYear());
        car.setLicensePlate(carDto.getLicensePlate());

        // Fetch garages by their IDs and update the association
        List<Garage> garages = garageRepository.findAllById(carDto.getGarageIds());

        // Convert List<Garage> to Set<Garage> if necessary
        car.setGarages(new HashSet<>(garages)); // Преобразувайте в Set

        car = carRepository.save(car);
        return convertToDTO(car);
    }

    // Delete car by its ID
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    // Helper method to convert CarDto to Car entity
    private Car convertToEntity(CarDto carDto) {
        Car car = new Car();
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setProductionYear(carDto.getProductionYear());
        car.setLicensePlate(carDto.getLicensePlate());
        return car;
    }

    public List<CarDto> getCarsByMake(String make) {
        List<Car> cars = carRepository.findByMake(make);
        return cars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<CarDto> getCarsByGarage(Long garageId) {
        List<Car> cars = carRepository.findByGarages_Id(garageId);
        return cars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    git 
    public List<CarDto> getCarsByYearRange(Integer fromYear, Integer toYear) {
        List<Car> cars = carRepository.findByProductionYearBetween(
                fromYear != null ? fromYear : Integer.MIN_VALUE,
                toYear != null ? toYear : Integer.MAX_VALUE
        );
        return cars.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
