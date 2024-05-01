package com.fastporte.fastportewebservice.repository;

import com.fastporte.fastportewebservice.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByDriverId(Long driverId);
    //List<Vehicle> findByType_cardQuantityCategory(String type_card, Long quantity, String category);
}
