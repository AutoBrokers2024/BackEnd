package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.StatusContract;
import com.fastporte.fastportewebservice.service.IStatusContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/statusContract")
public class StatusContractController {
    private final IStatusContractService statusContractService;

    public StatusContractController(IStatusContractService statusContractService) {
        this.statusContractService = statusContractService;
    }

    //Retornar todos los status
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List of Status Contract", description = "Method to list all status contracts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status contracts found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusContract.class))}),
            @ApiResponse(responseCode = "404", description = "Status contracts not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<StatusContract>> findAllStatus() {
        try {
            List<StatusContract> statusContracts = statusContractService.getAll();
            if (!statusContracts.isEmpty())
                return new ResponseEntity<>(statusContracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
