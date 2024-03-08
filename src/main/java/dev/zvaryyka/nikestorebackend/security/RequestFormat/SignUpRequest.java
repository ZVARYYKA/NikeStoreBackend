package dev.zvaryyka.nikestorebackend.security.RequestFormat;

import dev.zvaryyka.nikestorebackend.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class SignUpRequest {

    private Long id;

    @NotBlank(message = "Email cannot be blank") // Поле не должно быть пустым и не должно содержать только пробелы
    @Email(message = "Invalid email format") // Должен соответствовать формату электронной почты
    private String email;

    @NotBlank(message = "Name cannot be blank") // Поле не должно быть пустым и не должно содержать только пробелы
    @Size(max = 50, message = "Name cannot exceed 50 characters") // Длина поля не должна превышать 50 символов
    private String name;

    @NotBlank(message = "Surname cannot be blank") // Поле не должно быть пустым и не должно содержать только пробелы
    @Size(max = 50, message = "Surname cannot exceed 50 characters") // Длина поля не должна превышать 50 символов
    private String surname;

    @NotBlank(message = "Password cannot be blank") // Поле не должно быть пустым и не должно содержать только пробелы
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters") // Длина пароля должна быть от 6 до 100 символов
    private String password;

    private Set<Role> roles;
}

