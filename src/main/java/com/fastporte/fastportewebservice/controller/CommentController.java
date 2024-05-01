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
@RequestMapping("/api/comments")
@Api(tags="Comments", value="Web Service RESTful of Comments")
public class CommentController {

    private final ICommentService commentService;
    private final IClientService clientService;
    private final IDriverService driverService;

    public CommentController(ICommentService commentService, IClientService clientService, IDriverService driverService) {
        this.commentService = commentService;
        this.clientService = clientService;
        this.driverService = driverService;

    }

    //Retornar todos los comments
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="List of Comments", notes="Method to list all comments")
    @ApiResponses({
            @ApiResponse(code=201, message="Comments found"),
            @ApiResponse(code=404, message="Comments not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Comment>> findAllComments() { //Response entity: la clase por defecto de spring para responder desde un controlador de API
        try {
            List<Comment> comments = commentService.getAll();

            if (comments.size() > 0)
                return new ResponseEntity<>(comments, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar contrato por id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Comment by Id", notes="Method to find a comment by id")
    @ApiResponses({
            @ApiResponse(code=201, message="Comment found"),
            @ApiResponse(code=404, message="Comment not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Comment> findContractById(@PathVariable("id") Long id) {
        try {
            Optional<Comment> comment = commentService.getById(id);

            if (comment.isPresent()) //con isPresent se valida si es de tipo Contract o es nulo
                return new ResponseEntity<>(comment.get(), HttpStatus.OK); //se usa get porque es optional
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Retornar los commentarios por driver id
    @GetMapping(value = "/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Comment by Driver Id", notes="Method to find a comment by driver id")
    @ApiResponses({
            @ApiResponse(code=201, message="Comment found"),
            @ApiResponse(code=404, message="Comment not found"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<List<Comment>> findCommentById(@PathVariable("id") Long id) {
        try {
            List<Comment> comments = commentService.getAll();

            comments.removeIf(comment -> !comment.getDriver().getId().equals(id));
            if (comments.size() > 0)
                return new ResponseEntity<>(comments, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add/{clientId}/{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Insert Comment", notes="Method to insert a comment")
    @ApiResponses({
            @ApiResponse(code=201, message="Comment created"),
            @ApiResponse(code=404, message="Comment not created"),
            @ApiResponse(code=501, message="Internal server error")
    })
    public ResponseEntity<Comment> saveComment(@Valid @RequestBody Comment comment, @PathVariable("clientId") Long clientId,
                                               @PathVariable("driverId") Long driverId) {
        try {
            Optional<Client> client = clientService.getById(clientId);
            Optional<Driver> driver = driverService.getById(driverId);
            if (client.isPresent() && driver.isPresent()) {

                comment.setClient(client.get());
                comment.setDriver(driver.get());
                Comment newComment = commentService.save(comment);
                return new ResponseEntity<>(newComment, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
