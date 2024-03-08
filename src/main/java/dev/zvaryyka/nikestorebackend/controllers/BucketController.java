package dev.zvaryyka.nikestorebackend.controllers;

import dev.zvaryyka.nikestorebackend.dto.BucketDTO;
import dev.zvaryyka.nikestorebackend.dto.SneakersDTO;
import dev.zvaryyka.nikestorebackend.models.Bucket;
import dev.zvaryyka.nikestorebackend.models.Person;
import dev.zvaryyka.nikestorebackend.models.Sneakers;
import dev.zvaryyka.nikestorebackend.repositories.PeopleRepository;
import dev.zvaryyka.nikestorebackend.repositories.SneakersRepository;
import dev.zvaryyka.nikestorebackend.services.BucketService;
import dev.zvaryyka.nikestorebackend.services.PeopleService;
import dev.zvaryyka.nikestorebackend.util.person.PersonErrorResponse;
import dev.zvaryyka.nikestorebackend.util.person.PersonNotFoundException;
import dev.zvaryyka.nikestorebackend.util.sneakers.SneakersErrorResponse;
import dev.zvaryyka.nikestorebackend.util.sneakers.SneakersNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bucket")
@Tag(name = "Bucket Controller", description = "Work with user bucket")
public class BucketController {
    private final BucketService bucketsService;
    private final SneakersRepository sneakersRepository;

    private final PeopleService peopleService;


    @Autowired
    public BucketController(BucketService bucketsService, SneakersRepository sneakersRepository, PeopleService peopleService) {
        this.bucketsService = bucketsService;
        this.sneakersRepository = sneakersRepository;
        this.peopleService = peopleService;
    }
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get user bucket", description = "Get all info about user bucket")
    public List<Bucket> getUserBucket(Principal principal) {
       Person person = peopleService.findByEmail(principal.getName());
       return bucketsService.findAllByUserAndStatusFalse(person);

    }
    @PostMapping("/addNew")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add new in bucket", description = "Add new item in user bucket")
    public ResponseEntity<Bucket> addNewThing(Principal principal, @RequestBody SneakersDTO sneakersDTO) { //TODO Fix adding new, when table is empty
        Person person = peopleService.findByEmail(principal.getName());
        Sneakers sneakers = sneakersRepository.getById(sneakersDTO.getSneakersId());

        Bucket bucket = new Bucket(person, sneakers);

        bucketsService.save(bucket);

        return new ResponseEntity<>(bucket, HttpStatus.CREATED);
    }
    @DeleteMapping("/deleteThing")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete item in bucket", description = "Delete item in user bucket")
    public ResponseEntity<Integer>deleteThing(@RequestBody BucketDTO bucketDTO) {
        bucketsService.deleteById(bucketDTO.getBucketId());
        return new ResponseEntity<>(bucketDTO.getBucketId(),HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> personHandleException(PersonNotFoundException e) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person not found!",
                System.currentTimeMillis()

        );
        return new ResponseEntity<>(personErrorResponse,HttpStatus.NOT_FOUND);

    }
}
