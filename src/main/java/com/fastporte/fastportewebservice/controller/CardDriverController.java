package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.Card;
import com.fastporte.fastportewebservice.entities.CardDriver;
import com.fastporte.fastportewebservice.entities.Driver;
import com.fastporte.fastportewebservice.service.ICardDriverService;
import com.fastporte.fastportewebservice.service.ICardService;
import com.fastporte.fastportewebservice.service.IDriverService;
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
@RequestMapping("/api/cardsDriver")
@Tag(name="Cards Driver", description="Web Service RESTful of Cards Driver")
public class CardDriverController {
    private final ICardDriverService cardDriverService;
    private final IDriverService driverService;
    private final ICardService cardService;

    public CardDriverController(ICardDriverService cardDriverService, IDriverService driverService, ICardService cardService) {
        this.cardDriverService = cardDriverService;
        this.driverService = driverService;
        this.cardService = cardService;
    }

    @Operation(summary = "List of Cards Driver", description = "Method to list all cards driver")
    @ApiResponse(responseCode = "200", description = "Cards driver found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CardDriver.class))})
    @ApiResponse(responseCode = "204", description = "Cards driver not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<CardDriver>> findAll() {
        try {
            List<CardDriver> cardsDriver = cardDriverService.getAll();
            if (!cardsDriver.isEmpty())
                return new ResponseEntity<>(cardsDriver, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Card Driver by Id", description = "Method to find a card driver by id")
    @ApiResponse(responseCode = "200", description = "Card Driver found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CardDriver.class))})
    @ApiResponse(responseCode = "404", description = "Card Driver not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<List<CardDriver>> getCardsByDriverId(@PathVariable("id") Long id) {

        try {
            List<CardDriver> cardsDriver = cardDriverService.getAll();
            cardsDriver.removeIf(cardDriver -> !cardDriver.getDriver().getId().equals(id));
            if (cardsDriver.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cardsDriver, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Insert Card Driver", description = "Method to insert a card driver")
    @ApiResponse(responseCode = "201", description = "Card driver created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CardDriver.class))})
    @ApiResponse(responseCode = "404", description = "Card driver not created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/{idDriver}/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDriver> addCardToDriver(@PathVariable("idDriver") Long idDriver,
                                                      @Valid @RequestBody Card card) {
        try {
            Optional<Driver> driver = driverService.getById(idDriver);
            if (driver.isPresent()) {
                Long id = cardService.save(card).getId();
                CardDriver cardDriver = new CardDriver();
                cardDriver.setDriver(driver.get());
                cardDriver.setCard(cardService.getById(id).get());
                cardDriverService.save(cardDriver);
                return new ResponseEntity<>(cardDriver, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Card Driver", description = "Method to delete a card driver")
    @ApiResponse(responseCode = "200", description = "Card driver deleted")
    @ApiResponse(responseCode = "404", description = "Card driver not deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping(value = "/{idDriver}/delete/{idCard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCardFromDriver(@PathVariable("idDriver") Long idDriver,
                                                     @PathVariable("idCard") Long idCard) {
        try {
            Optional<Driver> driver = driverService.getById(idDriver);
            Optional<Card> card = cardService.getById(idCard);

            if (driver.isPresent() && card.isPresent()) {
                List<CardDriver> cardsDriver = cardDriverService.getAll();
                cardsDriver.removeIf(cardDriver ->
                        !(cardDriver.getDriver().getId().equals(idDriver) &&
                                cardDriver.getCard().getId().equals(idCard)));
                cardDriverService.delete(cardsDriver.get(0).getId());
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
