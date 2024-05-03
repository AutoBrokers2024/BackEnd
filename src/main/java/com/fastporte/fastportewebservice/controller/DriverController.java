package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Driver;
import com.fastporte.fastportewebservice.service.IDriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final IDriverService driverService;

    public DriverController(IDriverService driverService) {
        this.driverService = driverService;
    }

    // Retornar todos los drivers
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List of Drivers", description = "Method to list all drivers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drivers found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))}),
            @ApiResponse(responseCode = "204", description = "Drivers not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Driver>> getAllDrivers() {
        try {
            List<Driver> drivers = driverService.getAll();
            if (!drivers.isEmpty()) {
                return new ResponseEntity<>(drivers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retornar driver por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Driver by Id", description = "Method to find a driver by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))}),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Driver> findDriverById(@PathVariable("id") Long id) {
        try {
            Optional<Driver> driver = driverService.getById(id);
            if (driver.isPresent())
                return new ResponseEntity<>(driver.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Driver", description = "Method to create a driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Driver created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))}),
            @ApiResponse(responseCode = "404", description = "Driver not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Driver> insertDriver(@Valid @RequestBody Driver driver) {
        try {
            Driver driverNew = driverService.save(driver);
            return ResponseEntity.status(HttpStatus.CREATED).body(driverNew);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Driver", description = "Method to update a driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))}),
            @ApiResponse(responseCode = "404", description = "Driver not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Driver> updateDriver(@PathVariable("id") Long id,
                                               @Valid @RequestBody Driver driver) {
        try {
            Optional<Driver> driverUpdate = driverService.getById(id);
            if (!driverUpdate.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            driver.setId(id);
            driverService.save(driver);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Driver", description = "Method to delete a driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver deleted"),
            @ApiResponse(responseCode = "404", description = "Driver not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Driver> deleteDriver(@PathVariable("id") Long id) {
        try {
            Optional<Driver> driverDelete = driverService.getById(id);
            if (!driverDelete.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            driverService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retornar driver por email y password [ POR VER ]
    @GetMapping(value = "/searchEmailPassword/{email}/{password}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> findDriverByEmailAndPassword(
            @PathVariable("email") String email,
            @PathVariable("password") String password) {
        try {
            Driver driver = driverService.findByEmailAndPassword(email, password);
            if (driver == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
