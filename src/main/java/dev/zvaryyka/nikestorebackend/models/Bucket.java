package dev.zvaryyka.nikestorebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "bucket")
@Getter
@Setter
@NoArgsConstructor
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bucket_id")
    private int bucketId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Person user;

    @ManyToOne
    @JoinColumn(name = "sneakers_id")
    private Sneakers sneakers;

    @OneToMany(mappedBy = "bucket")
    @JsonIgnore
    private List<OrderDetails> orderDetailsList;

    @Column(name = "status")
    @JsonIgnore
    private boolean status;
    public Bucket(Person person, Sneakers sneakers) {
        this.user = person;
        this.sneakers = sneakers;
    }
}
