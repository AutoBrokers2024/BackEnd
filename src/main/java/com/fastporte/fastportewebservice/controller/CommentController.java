package com.fastporte.fastportewebservice.controller;

import com.fastporte.fastportewebservice.entities.*;
import com.fastporte.fastportewebservice.service.*;
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
@RequestMapping("/api/comments")
@Tag(name="Comments", description="Web Service RESTful of Comments")
public class CommentController {

    private final ICommentService commentService;
    private final IClientService clientService;
    private final IDriverService driverService;

    public CommentController(ICommentService commentService, IClientService clientService, IDriverService driverService) {
        this.commentService = commentService;
        this.clientService = clientService;
        this.driverService = driverService;
    }

    @Operation(summary = "List of Comments", description = "Method to list all comments")
    @ApiResponse(responseCode = "200", description = "Comments found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))})
    @ApiResponse(responseCode = "204", description = "Comments not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> findAllComments() {
        try {
            List<Comment> comments = commentService.getAll();

            if (!comments.isEmpty())
                return new ResponseEntity<>(comments, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Comment by Id", description = "Method to find a comment by id")
    @ApiResponse(responseCode = "200", description = "Comment found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))})
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> findCommentById(@PathVariable("id") Long id) {
        try {
            Optional<Comment> comment = commentService.getById(id);

            if (comment.isPresent())
                return new ResponseEntity<>(comment.get(), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Comment by Driver Id", description = "Method to find comments by driver id")
    @ApiResponse(responseCode = "200", description = "Comments found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))})
    @ApiResponse(responseCode = "204", description = "Comments not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping(value = "/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> findCommentsByDriverId(@PathVariable("id") Long id) {
        try {
            List<Comment> comments = commentService.getAll();

            comments.removeIf(comment -> !comment.getDriver().getId().equals(id));
            if (!comments.isEmpty())
                return new ResponseEntity<>(comments, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Insert Comment", description = "Method to insert a comment")
    @ApiResponse(responseCode = "201", description = "Comment created",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))})
    @ApiResponse(responseCode = "404", description = "Comment not created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping(value = "/add/{clientId}/{driverId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
