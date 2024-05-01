package com.fastporte.fastportewebservice.service;


import com.fastporte.fastportewebservice.entities.Vehicle;

import java.util.List;

public interface IVehicleService extends CrudService<Vehicle>{
    List<Vehicle> findByDriverId(Long driverId) throws Exception;
    //List<Vehicle> findByType_cardQuantityCategory(String type_card, Long quantity, String category) throws Exception;

}
