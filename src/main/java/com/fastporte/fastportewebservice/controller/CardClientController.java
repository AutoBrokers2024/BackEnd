package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Card;
import com.fastporte.fastportewebservice.entities.CardClient;
import com.fastporte.fastportewebservice.entities.Client;
import com.fastporte.fastportewebservice.service.ICardClientService;
import com.fastporte.fastportewebservice.service.ICardService;
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
@RequestMapping("/api/cardsClient")
@Tag(name="Cards Client", description="Web Service RESTful of Cards Clients")
public class CardClientController {
    private final ICardClientService cardClientService;
    private final IClientService clientService;
    private final ICardService cardService;

    public CardClientController(ICardClientService cardClientService, IClientService clientService, ICardService cardService) {
        this.cardClientService = cardClientService;
        this.clientService = clientService;
        this.cardService = cardService;
    }

    @Operation(summary = "List of Card Client", description = "Method to list all cards clients")
    @ApiResponse(responseCode = "200", description = "Cards Clients found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CardClient.class))})
    @ApiResponse(responseCode = "204", description = "Cards Clients not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<CardClient>> findAll() {
        try {
            List<CardClient> cardsClient = cardClientService.getAll();
            if (!cardsClient.isEmpty())
                return new ResponseEntity<>(cardsClient, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Card Client by Id", description = "Method to find a card client by id")
    @ApiResponse(responseCode = "200", description = "Card Client found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CardClient.class))})
    @ApiResponse(responseCode = "404", description = "Card Client not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<List<CardClient>> getCardsByClientId(@PathVariable("id") Long id) {

        try {
            List<CardClient> cardsClient = cardClientService.getAll();
            cardsClient.removeIf(cardClient -> !cardClient.getClient().getId().equals(id));
            if (cardsClient.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cardsClient, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Insert Card Client", description = "Method to insert a card client")
    @ApiResponse(responseCode = "201", description = "Card Client created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CardClient.class))})
    @ApiResponse(responseCode = "404", description = "Card Client not created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{idClient}/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardClient> addCardToClient(@PathVariable("idClient") Long idClient,
                                                      @Valid @RequestBody Card card) {
        try {
            Optional<Client> client = clientService.getById(idClient);
            if (client.isPresent()) {
                Long id = cardService.save(card).getId();
                CardClient cardClient = new CardClient();
                cardClient.setClient(client.get());
                cardClient.setCard(cardService.getById(id).get());
                cardClientService.save(cardClient);
                return new ResponseEntity<>(cardClient, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Card Client", description = "Method to delete a card client")
    @ApiResponse(responseCode = "200", description = "Card Client deleted")
    @ApiResponse(responseCode = "404", description = "Card Client not deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping(value = "/{idClient}/delete/{idCard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCardFromClient(@PathVariable("idClient") Long idClient,
                                                     @PathVariable("idCard") Long idCard) {
        try {
            Optional<Client> client = clientService.getById(idClient);
            Optional<Card> card = cardService.getById(idCard);

            if (client.isPresent() && card.isPresent()) {
                List<CardClient> cardsClient = cardClientService.getAll();
                cardsClient.removeIf(cardClient ->
                        !(cardClient.getClient().getId().equals(idClient) &&
                                cardClient.getCard().getId().equals(idCard)));
                cardClientService.delete(cardsClient.get(0).getId());
                cardService.delete(idCard);

                return new ResponseEntity<>(HttpStatus.OK);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}