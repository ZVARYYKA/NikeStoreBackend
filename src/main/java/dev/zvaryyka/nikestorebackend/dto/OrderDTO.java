package dev.zvaryyka.nikestorebackend.dto;

import dev.zvaryyka.nikestorebackend.models.OrderDetails;
import dev.zvaryyka.nikestorebackend.models.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter

public class OrderDTO {

    private int userId;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "About cannot be blank")
    private String about;


}
