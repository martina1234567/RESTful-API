package com.example.car_managment.repository;

import com.example.car_managment.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

// Find cars by make
    List<Car> findByMake(String make);
    List<Car> findByGarages_Id(Long garageId);
    List<Car> findByProductionYearBetween(int fromYear, int toYear);

}