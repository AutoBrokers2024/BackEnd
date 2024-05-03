package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.*;
import com.fastporte.fastportewebservice.service.*;
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
@RequestMapping("/api/experience")
public class ExperienceController {
    private final IExperienceService experienceService;
    private final IDriverService driverService;

    public ExperienceController(IExperienceService experienceService, IDriverService driverService) {
        this.experienceService = experienceService;
        this.driverService = driverService;
    }

    //Retornar experience por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Experience by Id", description = "Method to find an experience by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Experience found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Experience.class))}),
            @ApiResponse(responseCode = "404", description = "Experience not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Experience>> findExperienceById(@PathVariable("id") Long id) {
        try {
            List<Experience> experience = experienceService.findByDriverId(id);
            if (!experience.isEmpty())
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
    @Operation(summary = "Insert Experience", description = "Method to insert an experience")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Experience created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Experience.class))}),
            @ApiResponse(responseCode = "404", description = "Experience not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(summary = "Update Experience", description = "Method to update an experience")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Experience updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Experience.class))}),
            @ApiResponse(responseCode = "404", description = "Experience not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(summary = "Delete Experience", description = "Method to delete an experience")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Experience deleted"),
            @ApiResponse(responseCode = "404", description = "Experience not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
