package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Driver;
import com.fastporte.fastportewebservice.service.IDriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags="Driver", value="Web Service RESTful of Drivers")
public class DriverController {
    private final IDriverService driverService;

    public DriverController(IDriverService driverService) {
        this.driverService = driverService;
    }

    // Retornar todos los drivers
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="List of Drivers", notes="Method to list all drivers")
    @ApiResponses({
            @ApiResponse(code=201, message="Drivers found"),
            @ApiResponse(code=404, message="Drivers not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Driver>> getAllDrivers() {
        try {
            List<Driver> drivers = driverService.getAll();
            if (drivers.size() > 0) {
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
    @ApiOperation(value="Driver by Id", notes="Method to find a driver by id")
    @ApiResponses({
            @ApiResponse(code=201, message="Driver found"),
            @ApiResponse(code=404, message="Driver not found"),
            @ApiResponse(code=501, message="Internal server error")
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
    @ApiOperation(value="Create Driver", notes="Method to create a driver")
    @ApiResponses({
            @ApiResponse(code=201, message="Driver created"),
            @ApiResponse(code=404, message="Driver not created"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Driver> insertDriver(@RequestBody Driver driver) {
        try {
            Driver driverNew = driverService.save(driver);
            return ResponseEntity.status(HttpStatus.CREATED).body(driverNew);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Update Driver", notes="Method to update a driver")
    @ApiResponses({
            @ApiResponse(code=201, message="Driver updated"),
            @ApiResponse(code=404, message="Driver not updated"),
            @ApiResponse(code=501, message="Internal server error")
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
    @ApiOperation(value="Delete Driver", notes="Method to delete a driver")
    @ApiResponses({
            @ApiResponse(code=201, message="Driver deleted"),
            @ApiResponse(code=404, message="Driver not deleted"),
            @ApiResponse(code=501, message="Internal server error")
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
