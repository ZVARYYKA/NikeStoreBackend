package dev.zvaryyka.nikestorebackend.controllers;

import dev.zvaryyka.nikestorebackend.dto.OrderDTO;
import dev.zvaryyka.nikestorebackend.models.Order;
import dev.zvaryyka.nikestorebackend.models.Person;
import dev.zvaryyka.nikestorebackend.repositories.PeopleRepository;
import dev.zvaryyka.nikestorebackend.services.OrderService;
import dev.zvaryyka.nikestorebackend.services.PeopleService;
import dev.zvaryyka.nikestorebackend.util.person.PersonErrorResponse;
import dev.zvaryyka.nikestorebackend.util.person.PersonNotFoundException;
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

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order Controller", description = "Work with user orders")
public class OrderController {

    private final OrderService orderService;
    private final PeopleService peopleService;

    @Autowired
    public OrderController(OrderService orderService, PeopleService peopleService) {
        this.orderService = orderService;
        this.peopleService = peopleService;
    }

    // Добавление нового заказа
    @PostMapping("/createNewOrders")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add new order", description = "Add new user order. Add all bucket item and need info")
    public ResponseEntity<OrderDTO> addOrder(Principal principal, @Valid @RequestBody OrderDTO orderDTO) { //TODO Maybe rewrite
    Person user = peopleService.findByEmail(principal.getName());
        orderDTO.setUserId(Math.toIntExact(user.getId()));
        orderService.addOrderAndAddInfoInOrderDetails(orderDTO);
        return new ResponseEntity<>(orderDTO,HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all user orders", description = "Get all user orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> personHandleException(PersonNotFoundException e) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person not found!",
                System.currentTimeMillis()

        );
        return new ResponseEntity<>(personErrorResponse,HttpStatus.NOT_FOUND);

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
