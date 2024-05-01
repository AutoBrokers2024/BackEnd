package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.StatusContract;
import com.fastporte.fastportewebservice.service.IStatusContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags="Status Contract", value="Web Service RESTful of Status Contracts")
public class StatusContractController {
    private final IStatusContractService statusContractService;

    public StatusContractController(IStatusContractService statusContractService) {
        this.statusContractService = statusContractService;
    }

    //Retornar todos los status
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="List of Status Contract", notes="Method to list all status contracts")
    @ApiResponses({
            @ApiResponse(code=201, message="Status contracts found"),
            @ApiResponse(code=404, message="Status contracts not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<StatusContract>> findAllStatus() {
        try {
            List<StatusContract> statusContracts = statusContractService.getAll();
            if (statusContracts.size() > 0)
                return new ResponseEntity<>(statusContracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
