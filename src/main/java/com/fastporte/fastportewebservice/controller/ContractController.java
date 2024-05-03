package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.*;
import com.fastporte.fastportewebservice.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/contracts")
public class ContractController {

    private final IContractService contractService;
    private final IClientService clientService;
    private final IDriverService driverService;
    private final IStatusContractService statusContractService;
    private final INotificationService notificationService;

    public ContractController(IContractService contractService, IClientService clientService, IDriverService driverService, IStatusContractService statusContractService, INotificationService notificationService) {
        this.contractService = contractService;
        this.clientService = clientService;
        this.driverService = driverService;
        this.statusContractService = statusContractService;
        this.notificationService = notificationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List of Contracts", description = "Method to list all contracts")
    @ApiResponse(responseCode = "200", description = "Contracts found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "204", description = "Contracts not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Contract>> findAllContracts() {
        try {
            List<Contract> contracts = contractService.getAll();

            if (!contracts.isEmpty())
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Contract by Id", description = "Method to find a contract by id")
    @ApiResponse(responseCode = "200", description = "Contract found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "404", description = "Contract not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Contract> findContractById(@PathVariable("id") Long id) {
        try {
            Optional<Contract> contract = contractService.getById(id);

            if (contract.isPresent())
                return new ResponseEntity<>(contract.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/offer/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Contracts by status OFFER", description = "Method to find contracts by status OFFER for a client or driver")
    @ApiResponse(responseCode = "200", description = "Contracts found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "204", description = "Contracts not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Contract>> findContractByStatusOfferUser(@PathVariable("user") String user, @PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("OFFER"));
            if (user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
            if (!contracts.isEmpty())
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/pending/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Contracts by status PENDING", description = "Method to find contracts by status PENDING for a client or driver")
    @ApiResponse(responseCode = "200", description = "Contracts found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "204", description = "Contracts not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Contract>> findContractByStatusPending(@PathVariable("id") Long id, @PathVariable("user") String user) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("PENDING"));
            if (user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
            if (!contracts.isEmpty())
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/history/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Contracts by status HISTORY", description = "Method to find contracts by status HISTORY for a client or driver")
    @ApiResponse(responseCode = "200", description = "Contracts found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "204", description = "Contracts not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Contract>> findContractByStatusHistory(@PathVariable("id") Long id, @PathVariable("user") String user) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("HISTORY"));
            if (user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
            if (!contracts.isEmpty())
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add/{clientId}/{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Insert contract", description = "Method to insert a contract")
    @ApiResponse(responseCode = "201", description = "Contract created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "404", description = "Contract not created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Contract> insertContract(@PathVariable("clientId") Long clientId,
                                                   @PathVariable("driverId") Long driverId,
                                                   @Valid @RequestBody Contract contract) {
        try {
            Optional<Client> client = clientService.getById(clientId);
            Optional<Driver> driver = driverService.getById(driverId);
            if (client.isPresent() && driver.isPresent()) {
                contract.setClient(client.get());
                contract.setDriver(driver.get());
                contract.setVisible(true);
                contract.setStatus(statusContractService.getById(1L).get());
                contract.setNotification(notificationService.getById(0L).get());

                Contract contractNew = contractService.save(contract);
                return ResponseEntity.status(HttpStatus.CREATED).body(contractNew);
            } else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/notifications-client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Notifications by client id", description = "Method to find notifications by client id")
    @ApiResponse(responseCode = "200", description = "Notifications found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "204", description = "Notifications not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Contract>> findNotificationsByClient(@PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            if (!contracts.isEmpty())
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/unread-notifications/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Unread notifications by user id", description = "Method to find unread notifications by user id")
    @ApiResponse(responseCode = "200", description = "Unread notifications found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "204", description = "Unread notifications not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Contract>> findNotificationsUnreadByUser(@PathVariable("id") Long id,
                                                                        @PathVariable("user") String user) {
        try {
            List<Contract> contracts = contractService.getAll();
            if (user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
                contracts.removeIf(contract -> contract.getNotification().getId().equals(1L));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
                contracts.removeIf(contract -> contract.getNotification().getId().equals(1L));
            }

            if (!contracts.isEmpty())
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/notifications-driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Notifications by driver id", description = "Method to find notifications by driver id")
    @ApiResponse(responseCode = "200", description = "Notifications found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "204", description = "Notifications not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Contract>> findNotificationsByDriver(@PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));

            if (!contracts.isEmpty())
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idContract}/change-status-offer-to-pending/driver={idDriver}")
    @Operation(summary = "Update notification status of OFFER to PENDING", description = "Method to update notification status of OFFER to PENDING")
    @ApiResponse(responseCode = "200", description = "Notification status updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "404", description = "Notification status not updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Contract> updateContractStatusOfferToPending(@PathVariable("idContract") Long idContract,
                                                                       @PathVariable("idDriver") Long idDriver) {
        try {

            Optional<Contract> contract = contractService.getById(idContract);
            Optional<StatusContract> statusContract = statusContractService.getById(2L);

            if (contract.isPresent() && statusContract.isPresent()) {
                Contract contractUpdate = contract.get();
                contractUpdate.setId(idContract);
                contractUpdate.setStatus(statusContract.get());
                contractUpdate.setDriver(driverService.getById(idDriver).get());
                contractService.save(contractUpdate);
                return new ResponseEntity<>(contractUpdate, HttpStatus.OK);

            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idContract}/update-status/{idContractStatus}")
    @Operation(summary = "Update notification status of PENDING to HISTORY", description = "Method to update notification status of PENDING to HISTORY")
    @ApiResponse(responseCode = "200", description = "Notification status updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "404", description = "Notification status not updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Contract> updateContractStatusPatch(@PathVariable("idContract") Long idContract,
                                                              @PathVariable("idContractStatus") Long idContractStatus) {
        try {

            if (idContractStatus < 1 || idContractStatus > 3) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Contract> contract = contractService.getById(idContract);
            Optional<StatusContract> statusContract = statusContractService.getById(idContractStatus);

            if (contract.isPresent() && statusContract.isPresent()) {
                Contract contractUpdate = contract.get();
                contractUpdate.setId(idContract);
                contractUpdate.setStatus(statusContract.get());
                contractService.save(contractUpdate);
                return new ResponseEntity<>(contractUpdate, HttpStatus.OK);

            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idContract}/change-notification-status")
    @Operation(summary = "Update notification read status", description = "Method to update notification read status")
    @ApiResponse(responseCode = "200", description = "Notification status updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "404", description = "Notification status not updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Contract> updateContractNotificationPatch(@PathVariable("idContract") Long idContract) {
        try {

            Optional<Contract> contract = contractService.getById(idContract);

            if (contract.isPresent()) {

                Optional<Notification> notification;
                Contract contractUpdate = contract.get();
                contractUpdate.setId(idContract);

                if (contractUpdate.getNotification().getId() == 0) {
                    notification = notificationService.getById(1L);
                } else {
                    notification = notificationService.getById(0L);
                }

                contractUpdate.setNotification(notification.get());
                contractService.save(contractUpdate);
                return new ResponseEntity<>(contractUpdate, HttpStatus.OK);

            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/notification-read/{user}/{id}")
    @Operation(summary = "Update unread notifications of user", description = "Method to update unread notifications of user")
    @ApiResponse(responseCode = "200", description = "Notification status updated")
    @ApiResponse(responseCode = "404", description = "Notification status not updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Contract> updateContractNotificationPatch(@PathVariable("id") Long id,
                                                                    @PathVariable("user") String user) {

        try {
            List<Contract> contracts = contractService.getAll();
            Optional<Notification> notification = notificationService.getById(1L);
            if (user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
                contracts.removeIf(contract -> contract.getNotification().getId().equals(1L));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
                contracts.removeIf(contract -> contract.getNotification().getId().equals(1L));
            }

            if (!contracts.isEmpty()) {
                contracts.forEach(contract -> {
                    contract.setNotification(notification.get());
                    try {
                        contractService.save(contract);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{idContract}/change-visible")
    @Operation(summary = "Update visibility of contract", description = "Method to update visibility of contract")
    @ApiResponse(responseCode = "200", description = "Visibility of contract updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class)))
    @ApiResponse(responseCode = "404", description = "Visibility of contract not updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Contract> updateContractVisiblePatch(@PathVariable("idContract") Long idContract) {
        try {
            Optional<Contract> contract = contractService.getById(idContract);

            if (contract.isPresent()) {
                Contract contractUpdate = contract.get();
                contractUpdate.setId(idContract);
                contractUpdate.setVisible(false);
                contractService.save(contractUpdate);
                return new ResponseEntity<>(contractUpdate, HttpStatus.OK);

            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}