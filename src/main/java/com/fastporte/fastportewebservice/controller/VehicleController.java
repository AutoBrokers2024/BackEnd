package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Driver;
import com.fastporte.fastportewebservice.entities.Vehicle;
import com.fastporte.fastportewebservice.service.IDriverService;
import com.fastporte.fastportewebservice.service.IVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/vehicle")
@Tag(name="Vehicle", description="Web Service RESTful of Vehicles")
public class VehicleController {
    private final IVehicleService vehicleService;
    private final IDriverService driverService;

    public VehicleController(IVehicleService vehicleService, IDriverService driverService) {
        this.vehicleService = vehicleService;
        this.driverService = driverService;
    }

    @Operation(summary = "Vehicle by Id", description = "Method to find a vehicle by id")
    @ApiResponse(responseCode = "200", description = "Vehicle found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))})
    @ApiResponse(responseCode = "404", description = "Vehicle not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable("id") Long id) {
        try {
            Optional<Vehicle> vehicle = vehicleService.getById(id);
            if (vehicle.isPresent())
                return new ResponseEntity<>(vehicle.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Vehicle by driver id", description = "Method to find vehicles by driver id")
    @ApiResponse(responseCode = "200", description = "Vehicles found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))})
    @ApiResponse(responseCode = "204", description = "Vehicles not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Vehicle>> findVehicleByDriverId(@PathVariable("id") Long id) {
        try {
            List<Vehicle> vehicles = vehicleService.getAll();

            vehicles.removeIf(vehicle -> !vehicle.getDriver().getId().equals(id));

            if (!vehicles.isEmpty())
                return new ResponseEntity<>(vehicles, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Vehicle by Category & Quantity", description = "Method to find vehicles by category and quantity")
    @ApiResponse(responseCode = "200", description = "Vehicles found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))})
    @ApiResponse(responseCode = "204", description = "Vehicles not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/find/{type}/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Vehicle>> findByTypeCardQuantityCategory(
            @PathVariable("type") String type,
            @PathVariable("quantity") Long quantity) {
        try {
            List<Vehicle> vehicles = vehicleService.getAll();
            vehicles.removeIf(vehicle -> !vehicle.getType().equals(type));
            vehicles.removeIf(vehicle -> vehicle.getQuantity() < quantity);

            if (!vehicles.isEmpty())
                return new ResponseEntity<>(vehicles, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Insert Vehicle", description = "Method to insert a vehicle")
    @ApiResponse(responseCode = "201", description = "Vehicle created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> insertVehicle(@PathVariable("driverId") Long driverId,
                                                 @Valid @RequestBody Vehicle vehicle) {
        try {
            Optional<Driver> driver = driverService.getById(driverId);
            if (driver.isPresent()) {
                vehicle.setDriver(driver.get());
                Vehicle newVehicle = vehicleService.save(vehicle);
                return ResponseEntity.status(HttpStatus.CREATED).body(newVehicle);
            } else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update Vehicle", description = "Method to update a vehicle")
    @ApiResponse(responseCode = "200", description = "Vehicle updated",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehicle.class))})
    @ApiResponse(responseCode = "404", description = "Vehicle not updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id") Long id,
                                                 @Valid @RequestBody Vehicle vehicle) {
        try {
            Optional<Vehicle> vehicleToUpdate = vehicleService.getById(id);
            if (vehicleToUpdate.isPresent()) {
                vehicle.setId(id);
                vehicleService.save(vehicle);
                return new ResponseEntity<>(vehicle, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Vehicle", description = "Method to delete a vehicle")
    @ApiResponse(responseCode = "200", description = "Vehicle deleted")
    @ApiResponse(responseCode = "404", description = "Vehicle not deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") Long id) {
        try {
            Optional<Vehicle> vehicleToDelete = vehicleService.getById(id);
            if (vehicleToDelete.isPresent()) {
                vehicleService.delete(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}