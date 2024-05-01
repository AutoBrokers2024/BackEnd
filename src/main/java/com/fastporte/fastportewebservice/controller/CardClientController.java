package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Card;
import com.fastporte.fastportewebservice.entities.CardClient;
import com.fastporte.fastportewebservice.entities.Client;
import com.fastporte.fastportewebservice.service.ICardClientService;
import com.fastporte.fastportewebservice.service.ICardService;
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
@RequestMapping("/api/cardsClient")
@Api(tags="Cards Client", value="Web Service RESTful of Cards Clients")
public class CardClientController {
    private final ICardClientService cardClientService;
    private final IClientService clientService;
    private final ICardService cardService;

    public CardClientController(ICardClientService cardClientService, IClientService clientService, ICardService cardService) {
        this.cardClientService = cardClientService;
        this.clientService = clientService;
        this.cardService = cardService;
    }

    //Retornar todos los cardsClient
    @GetMapping(value = "/all", produces = "application/json")
    @ApiOperation(value="List of Card Client", notes="Method to list all cards clients")
    @ApiResponses({
            @ApiResponse(code=201, message="Cards Clients found"),
            @ApiResponse(code=404, message="Cards Clients not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<CardClient>> findAll() {
        try {
            List<CardClient> cardsClient = cardClientService.getAll();
            if (cardsClient.size() > 0)
                return new ResponseEntity<>(cardsClient, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener los cards de un client por id
    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value="Card Client by Id", notes="Method to find a card client by id")
    @ApiResponses({
            @ApiResponse(code=201, message="Card Client found"),
            @ApiResponse(code=404, message="Card Client not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<CardClient>> getCardsByClientId(@PathVariable("id") Long id) {

        try {
            List<CardClient> cardsClient = cardClientService.getAll();
            cardsClient.removeIf(cardClient -> !cardClient.getClient().getId().equals(id));
            if (cardsClient.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cardsClient, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Agregar un card a un client
    @PostMapping(value = "/{idClient}/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Insert Card Client", notes="Method to insert a card client")
    @ApiResponses({
            @ApiResponse(code=201, message="Card Client created"),
            @ApiResponse(code=404, message="Card Client not created"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<CardClient> addCardToClient(@PathVariable("idClient") Long idClient,
                                                      @Valid @RequestBody Card card) {
        try {
            Optional<Client> client = clientService.getById(idClient);
            if (client.isPresent()) {
                Long id = cardService.save(card).getId();
                System.out.println("Id: " + id);
                CardClient cardClient = new CardClient();
                cardClient.setClient(client.get());
                cardClient.setCard(cardService.getById(id).get());
                cardClientService.save(cardClient);
                id = 0L;
                return new ResponseEntity<>(cardClient, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Eliminar un card de un client
    @DeleteMapping(value = "/{idClient}/delete/{idCard}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Delete Card Client", notes="Method to delete a card client")
    @ApiResponses({
            @ApiResponse(code=201, message="Card Client deleted"),
            @ApiResponse(code=404, message="Card Client not deleted"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<HttpStatus> deleteCardFromClient(@PathVariable("idClient") Long idClient,
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
