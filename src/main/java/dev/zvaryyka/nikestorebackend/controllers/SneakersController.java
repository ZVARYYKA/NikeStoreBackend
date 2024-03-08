package dev.zvaryyka.nikestorebackend.controllers;

import dev.zvaryyka.nikestorebackend.models.Sneakers;
import dev.zvaryyka.nikestorebackend.services.SneakersService;
import dev.zvaryyka.nikestorebackend.util.sneakers.SneakersErrorResponse;
import dev.zvaryyka.nikestorebackend.util.sneakers.SneakersNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sneakers")
@Tag(name = "Sneakers Controller", description = "Controller, which work with sneakers info")
public class SneakersController {
    private final SneakersService sneakersService;

    @Autowired
    public SneakersController(SneakersService sneakersService) {
        this.sneakersService = sneakersService;
    }


    @GetMapping()
    @Operation(summary = "Get all sneakers", description = "Get all sneakers from database")
    public List<Sneakers> getAllSneakers() {
        return sneakersService.getAllSneakers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sneakers", description = "Get sneakers from sneakers id")
    public Sneakers getOneSneakers(@PathVariable("id") int id) {
        return sneakersService.getOneSneakers(id);
    }
    @ExceptionHandler
    private ResponseEntity<SneakersErrorResponse> sneakersHandleException(SneakersNotFoundException e) {
        SneakersErrorResponse sneakersErrorResponse = new SneakersErrorResponse(
                "Sneakers not found!",
                System.currentTimeMillis()

        );
        return new ResponseEntity<>(sneakersErrorResponse,HttpStatus.NOT_FOUND);

    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add new sneakers", description = "Add new sneakers")
    public ResponseEntity<Sneakers> createNewSneakers(@Valid @RequestBody Sneakers sneakers) {
        sneakersService.saveNewSneakers(sneakers);
        return new ResponseEntity<>(sneakers, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change sneakers", description = "Change sneakers, which was in database")
    public ResponseEntity<Sneakers> updateSneakers(@PathVariable("id") int id, @RequestBody Sneakers sneakers) {
        sneakersService.updateSneakers(id, sneakers);
        return new ResponseEntity<>(sneakers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete sneakers", description = "Delete sneakers from id")
    public ResponseEntity<Integer> deleteSneakers(@PathVariable("id") int id) {
        sneakersService.deleteSneakers(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
