package com.example.car_managment.service;

import com.example.car_managment.dto.GarageDto;
import com.example.car_managment.entity.Garage;
import com.example.car_managment.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarageService {

    @Autowired
    private GarageRepository garageRepository;

    public List<GarageDto> getAllGarages() {
        List<Garage> garages = garageRepository.findAll();
        return garages.stream().map(this::convertToDto).toList();
    }

    public GarageDto getGarageById(Long id) {
        Optional<Garage> garage = garageRepository.findById(id);
        return garage.map(this::convertToDto).orElse(null);
    }

    public List<GarageDto> getGaragesByCity(String city) {
        List<Garage> garages = garageRepository.findByCity(city);
        return garages.stream().map(this::convertToDto).toList();
    }

    public GarageDto createGarage(GarageDto garageDto) {
        Garage garage = new Garage();
        garage.setName(garageDto.getName());
        garage.setLocation(garageDto.getLocation());
        garage.setCity(garageDto.getCity());
        garage.setCapacity(garageDto.getCapacity());
        garage = garageRepository.save(garage);
        return convertToDto(garage);
    }

    public GarageDto updateGarage(Long id, GarageDto garageDto) {
        Optional<Garage> existingGarage = garageRepository.findById(id);
        if (existingGarage.isPresent()) {
            Garage garage = existingGarage.get();
            garage.setName(garageDto.getName());
            garage.setLocation(garageDto.getLocation());
            garage.setCity(garageDto.getCity());
            garage.setCapacity(garageDto.getCapacity());
            garage = garageRepository.save(garage);
            return convertToDto(garage);
        } else {
            return null;
        }
    }

    public boolean deleteGarage(Long id) {
        Optional<Garage> garage = garageRepository.findById(id);
        if (garage.isPresent()) {
            garageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private GarageDto convertToDto(Garage garage) {
        return new GarageDto(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
        );
    }
}