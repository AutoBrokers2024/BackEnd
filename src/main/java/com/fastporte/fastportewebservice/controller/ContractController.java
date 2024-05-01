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

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/contracts")
@Api(tags="Contract", value="Web Service RESTful of Contracts")
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

    //Retornar todos los contratos
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="List of Contracts", notes="Method to list all contracts")
    @ApiResponses({
            @ApiResponse(code=201, message="Contracts found"),
            @ApiResponse(code=404, message="Contracts not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Contract>> findAllContracts() { //Response entity: la clase por defecto de spring para responder desde un controlador de API
        try {
            List<Contract> contracts = contractService.getAll();

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar contrato por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Contract by Id", notes="Method to find a contract by id")
    @ApiResponses({
            @ApiResponse(code=201, message="Contract found"),
            @ApiResponse(code=404, message="Contract not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Contract> findContractById(@PathVariable("id") Long id) {
        try {
            Optional<Contract> contract = contractService.getById(id);

            if (contract.isPresent()) //con isPresent se valida si es de tipo Contract o es nulo
                return new ResponseEntity<>(contract.get(), HttpStatus.OK); //se usa get porque es optional
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar los contratos por driver con status OFFER
    /*
    @GetMapping(value = "/offer/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Contracts of driver by status OFFER", notes="Method to find contracts of driver by status OFFER")
    @ApiResponses({
            @ApiResponse(code=201, message="Contracts found"),
            @ApiResponse(code=404, message="Contracts not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Contract>> findContractByStatusOffer(@PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("OFFER"));
            contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     */

    //Retornar los contratos por user(client/driver) con status OFFER
    @GetMapping(value = "/offer/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Contracts of client by status OFFER", notes="Method to find contracts of client by status OFFER")
    @ApiResponses({
            @ApiResponse(code=201, message="Contracts found"),
            @ApiResponse(code=404, message="Contracts not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Contract>> findContractByStatusOfferUser(@PathVariable("user") String user, @PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("OFFER"));
            if (user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar los contratos por user(client/driver) con status PENDING
    @GetMapping(value = "/pending/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Contracts of driver or client by status PENDING", notes="Method to find contracts of driver by status PENDING")
    @ApiResponses({
            @ApiResponse(code=201, message="Contracts found"),
            @ApiResponse(code=404, message="Contracts not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Contract>> findContractByStatusPending(@PathVariable("id") Long id, @PathVariable("user") String user) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("PENDING"));
            if (user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
            //Quitar el campo Driver de los contratos
            //contracts.forEach(contract -> contract.setDriver(null));

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar los contratos por user (client/driver) con status HISTORY
    @GetMapping(value = "/history/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Contracts of driver or client by status HISTORY", notes="Method to find contracts of driver by status HISTORY")
    @ApiResponses({
            @ApiResponse(code=201, message="Contracts found"),
            @ApiResponse(code=404, message="Contracts not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Contract>> findContractByStatusHistory(@PathVariable("id") Long id, @PathVariable("user") String user) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getStatus().getStatus().equals("HISTORY"));
            if (user.equals("client")) {
                contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            } else if (user.equals("driver")) {
                contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            }
            //Quitar el campo Driver de los contratos
            //contracts.forEach(contract -> contract.setDriver(null));

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Crear un contrato por driver y cliente
    @PostMapping(value = "/add/{clientId}/{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Insert contract", notes="Method to insert a contract")
    @ApiResponses({
            @ApiResponse(code=201, message="Contract created"),
            @ApiResponse(code=404, message="Contract not created"),
            @ApiResponse(code=501, message="Internal server error")
    })
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
                System.out.println("Error Gaaa");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            System.out.println("Error GAAAA");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener las notificaciones de un client
    @GetMapping(value = "/notifications-client/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Notifications by client id", notes="Method to find notifications by client id")
    @ApiResponses({
            @ApiResponse(code=201, message="Notifications found"),
            @ApiResponse(code=404, message="Notifications not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Contract>> findNotificationsByClient(@PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getClient().getId().equals(id));
            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obetner las notificaciones no leídas de un client
    @GetMapping(value = "/unread-notifications/{user}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Unread notifications by user(client/driver) id", notes="Method to find unread notifications by user id")
    @ApiResponses({
            @ApiResponse(code=201, message="Unread notifications found"),
            @ApiResponse(code=404, message="Unread notifications not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
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

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener las notificaciones de un driver
    @GetMapping(value = "/notifications-driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Notifications by driver id", notes="Method to find notifications by driver id")
    @ApiResponses({
            @ApiResponse(code=201, message="Notifications found"),
            @ApiResponse(code=404, message="Notifications not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Contract>> findNotificationsByDriver(@PathVariable("id") Long id) {
        try {
            List<Contract> contracts = contractService.getAll();
            contracts.removeIf(contract -> !contract.getDriver().getId().equals(id));
            /*
            contracts.forEach(contract -> {
                contract.setClientTmp(contract.getClient());
            });
             */

            if (contracts.size() > 0)
                return new ResponseEntity<>(contracts, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Cambiar el status del contrato de OFFER a PENDING - aquí se añade al driver al contrato
/*
    @PutMapping(value = "/{idContract}/change-status-offer-to-pending/driver={idDriver}")
    @ApiOperation(value="Update notification status of OFFER to PENDING", notes="Method to update notification status of OFFER to PENDING")
    @ApiResponses({
            @ApiResponse(code=201, message="Notification status updated"),
            @ApiResponse(code=404, message="Notification status not updated"),
            @ApiResponse(code=501, message="Internal server error")
    })
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
 */


    //Actualizar el status de un contrato (solo de pending a history)
    @PutMapping(value = "/{idContract}/update-status/{idContractStatus}")
    @ApiOperation(value="Update notification status of PENDING to HISTORY", notes="Method to update notification status of PENDING to HISTORY")
    @ApiResponses({
            @ApiResponse(code=201, message="Notification status updated"),
            @ApiResponse(code=404, message="Notification status not updated"),
            @ApiResponse(code=501, message="Internal server error")
    })
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

    //Intercalar el status de una notificacion (de leida a no leida o viceversa)
    @PutMapping(value = "/{idContract}/change-notification-status")
    @ApiOperation(value="Update notification read status", notes="Method to update notification read status")
    @ApiResponses({
            @ApiResponse(code=201, message="Notification status updated"),
            @ApiResponse(code=404, message="Notification status not updated"),
            @ApiResponse(code=501, message="Internal server error")
    })
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

    //Cambiar el status de las notificaciones no leídas a leídas de un user
    @PutMapping(value = "/notification-read/{user}/{id}")
    @ApiOperation(value="Update unread notifications of user", notes="Method to update unread notifications of user")
    @ApiResponses({
            @ApiResponse(code=201, message="Notification status updated"),
            @ApiResponse(code=404, message="Notification status not updated"),
            @ApiResponse(code=501, message="Internal server error")
    })
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

            if (contracts.size() > 0) {
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

    //Cambiar el campo visible de un contrato a false
    @PutMapping(value = "/{idContract}/change-visible")
    @ApiOperation(value="Update visibility of contract", notes="Method to update visibility of contract")
    @ApiResponses({
            @ApiResponse(code=201, message="Visibility of contract updated"),
            @ApiResponse(code=404, message="Visibility of contract not updated"),
            @ApiResponse(code=501, message="Internal server error")
    })
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
