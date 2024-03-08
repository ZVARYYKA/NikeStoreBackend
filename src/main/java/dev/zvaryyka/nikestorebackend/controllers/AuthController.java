package dev.zvaryyka.nikestorebackend.controllers;

import dev.zvaryyka.nikestorebackend.models.ERole;
import dev.zvaryyka.nikestorebackend.models.Person;
import dev.zvaryyka.nikestorebackend.models.Role;
import dev.zvaryyka.nikestorebackend.repositories.PeopleRepository;
import dev.zvaryyka.nikestorebackend.repositories.RoleRepository;
import dev.zvaryyka.nikestorebackend.security.RequestFormat.LoginRequest;
import dev.zvaryyka.nikestorebackend.security.RequestFormat.SignUpRequest;
import dev.zvaryyka.nikestorebackend.security.ResponseFormats.JwtResponse;
import dev.zvaryyka.nikestorebackend.security.ResponseFormats.MessageResponse;
import dev.zvaryyka.nikestorebackend.security.UserDetailsImpl;
import dev.zvaryyka.nikestorebackend.security.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "Endpoints for user authentication and registration")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PeopleRepository userRepository; //TODO change to Service

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    @Operation(summary = "Authenticate user", description = "Endpoint to authenticate user")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity
                    .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(),userDetails.getName(),userDetails.getSurname(),
                            roles));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Error: Unauthorized - Invalid email or password"));
        }
    }

    @PostMapping("/signup") //TODO REWRITE THIS LATER
    @Operation(summary = "Register user", description = "Endpoint to register new user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Person user = new Person(signUpRequest.getEmail(),signUpRequest.getSurname(),signUpRequest.getName(),encoder.encode(signUpRequest.getPassword()));

        Set<Role> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else if (role.equals("mod")) {
                    Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
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
