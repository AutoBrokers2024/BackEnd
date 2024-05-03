package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Client;
import com.fastporte.fastportewebservice.service.IClientService;
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
@RequestMapping("/api/clients")
@Tag(name="Client", description="Web Service RESTful of Clients")
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "List of Clients", description = "Method to list all clients")
    @ApiResponse(responseCode = "200", description = "Clients found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "204", description = "Clients not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = clientService.getAll();
            if (!clients.isEmpty()) {
                return new ResponseEntity<>(clients, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Client by Id", description = "Method to find a client by id")
    @ApiResponse(responseCode = "200", description = "Client found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @Operation(summary = "Insert Client", description = "Method to insert a client")
    @ApiResponse(responseCode = "201", description = "Client created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> insertClient(@Valid @RequestBody Client client) {
        try {
            Client newClient = clientService.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update Client", description = "Method to update a client")
    @ApiResponse(responseCode = "200", description = "Client updated",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @Operation(summary = "Delete Client", description = "Method to delete a client")
    @ApiResponse(responseCode = "200", description = "Client deleted")
    @ApiResponse(responseCode = "404", description = "Client not deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
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

    @Operation(summary = "Client by Email and Password", description = "Method to find a client by email and password")
    @ApiResponse(responseCode = "200", description = "Client found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/searchEmailPassword/{email}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
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