package com.example.car_managment.repository;

import com.example.car_managment.dto.MonthlyRequestsReportDto;
import com.example.car_managment.entity.Car;
import com.example.car_managment.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.car_managment.entity.Maintenance;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByCar_Id(Long carId);

    List<Maintenance> findByGarage_Id(Long garageId);

    List<Maintenance> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);

    List<Maintenance> findByCarIdAndGarageIdAndScheduledDateBetween(Long carId, Long garageId, LocalDate startDate, LocalDate endDate);

    int countByGarageIdAndScheduledDate(Long garageId, LocalDate scheduledDate);

@Query("SELECT COUNT(m) FROM Maintenance m WHERE m.garage.id = :garageId AND m.scheduledDate >= :startDate AND m.scheduledDate < :endDate")
int countByGarageIdAndDateRange(@Param("garageId") Long garageId,
                                @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);

}

