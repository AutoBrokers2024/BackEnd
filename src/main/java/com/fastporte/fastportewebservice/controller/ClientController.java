package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Client;
import com.fastporte.fastportewebservice.service.IClientService;
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
@RequestMapping("/api/clients")
@Api(tags="Client", value="Web Service RESTful of Clients")
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    // Retornar todos los clientes
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="List of Clients", notes="Method to list all clients")
    @ApiResponses({
            @ApiResponse(code=201, message="Clients found"),
            @ApiResponse(code=404, message="Clients not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.getAll();
            if (clients.size() > 0) {
                return new ResponseEntity<>(clients, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retornar cliente por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Client by Id", notes="Method to find a client by id")
    @ApiResponses({
            @ApiResponse(code=201, message="Client found"),
            @ApiResponse(code=404, message="Client not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Client> findClientById(@PathVariable("id") Long id) {
        try {
            Optional<Client> client = clientService.getById(id);
            if (client.isPresent())
                return new ResponseEntity<>(client.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Insertar cliente
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Insert Client", notes="Method to insert a client")
    @ApiResponses({
            @ApiResponse(code=201, message="Client created"),
            @ApiResponse(code=404, message="Client not created"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Client> insertClient(@Valid @RequestBody Client client) {
        try {
            Client clientNew = clientService.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientNew);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar cliente
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Update Client", notes="Method to update a client")
    @ApiResponses({
            @ApiResponse(code=201, message="Client updated"),
            @ApiResponse(code=404, message="Client not updated"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Client> updateClient(@PathVariable("id") Long id,
                                               @Valid @RequestBody Client client) {
        try {
            Optional<Client> clientUpdate = clientService.getById(id);
            if (!clientUpdate.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            client.setId(id);
            clientService.save(client);
            return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar cliente
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Delete Client", notes="Method to delete a client")
    @ApiResponses({
            @ApiResponse(code=201, message="Client deleted"),
            @ApiResponse(code=404, message="Client not deleted"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Client> deleteClient(@PathVariable("id") Long id) {
        try {
            Optional<Client> clientDelete = clientService.getById(id);
            if (!clientDelete.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retornar client por email y password
    @GetMapping(value = "/searchEmailPassword/{email}/{password}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> findClientByEmailAndPassword(
            @PathVariable("email") String email,
            @PathVariable("password") String password) {
        try {
            Client client = clientService.findByEmailAndPassword(email, password);
            if (client == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           return new ResponseEntity<>(client, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
