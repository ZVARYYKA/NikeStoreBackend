package dev.zvaryyka.nikestorebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "sneakers")
@Getter
@Setter
@NoArgsConstructor
public class Sneakers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sneakers_id")
    private int sneakersId;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    @Column(name = "price")
    private BigDecimal price;

    @NotBlank(message = "About cannot be blank")
    @Column(name = "about")
    private String about;

    @NotBlank(message = "Image path cannot be blank")
    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(mappedBy = "sneakers", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bucket> buckets;

}
