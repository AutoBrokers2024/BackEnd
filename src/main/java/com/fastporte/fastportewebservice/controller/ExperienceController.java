package com.fastporte.fastportewebservice.controller;


import com.fastporte.fastportewebservice.entities.*;
import com.fastporte.fastportewebservice.service.*;
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
@RequestMapping("/api/experience")
@Api(tags="Experience", value="Web Service RESTful of Experiences")
public class ExperienceController {
    private final IExperienceService experienceService;
    private final IDriverService driverService;

    public ExperienceController(IExperienceService experienceService, IDriverService driverService) {
        this.experienceService = experienceService;
        this.driverService = driverService;
    }

    //Retornar experience por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Experience by Id", notes="Method to find a experience by id")
    @ApiResponses({
            @ApiResponse(code=201, message="Experience found"),
            @ApiResponse(code=404, message="Experience not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Experience>> findExperienceById(@PathVariable("id") Long id) {
        try {

            List<Experience> experience = experienceService.findByDriverId(id);
            if (experience.size() > 0)
                return new ResponseEntity<>(experience, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Insertar experience
    @PostMapping(value = "/{driverId}",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Insert Experience", notes="Method to insert a Experience")
    @ApiResponses({
            @ApiResponse(code=201, message="Experience created"),
            @ApiResponse(code=404, message="Experience not created"),
            @ApiResponse(code=501, message="Experience server error")
    })
    public ResponseEntity<Experience> insertExperience(@PathVariable("driverId") Long driverId,
                                                     @Valid @RequestBody Experience experience) {
        try {

            Optional<Driver> driver = driverService.getById(driverId);
        if (driver.isPresent()) {
            experience.setDriver(driver.get());
            Experience experienceNew = experienceService.save(experience);
            return ResponseEntity.status(HttpStatus.CREATED).body(experienceNew);
        } else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Actualizar experience
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Update Experience", notes="Method to update a experience")
    @ApiResponses({
            @ApiResponse(code=201, message="Experience updated"),
            @ApiResponse(code=404, message="Experience not updated"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Experience> updateExperience(@PathVariable("id") Long id,
                                                       @Valid @RequestBody Experience experience) {
        try {
            Optional<Experience> experienceUpdate = experienceService.getById(id);
            if (!experienceUpdate.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            experience.setId(id);
            experienceService.save(experience);
            return new ResponseEntity<>(experience, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Eliminar experience
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Delete Experience", notes="Method to delete a experience")
    @ApiResponses({
            @ApiResponse(code=201, message="Experience deleted"),
            @ApiResponse(code=404, message="Experience not deleted"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Experience> deleteExperience(@PathVariable("id") Long id) {
        try {
            Optional<Experience> experienceDelete = experienceService.getById(id);
            if (!experienceDelete.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            experienceService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
