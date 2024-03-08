package dev.zvaryyka.nikestorebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

@Entity
@Table(name = "orderdetails")
public class OrderDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private int orderDetailsId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;

    public OrderDetails() {
    }

    public OrderDetails(Order order, Bucket bucket) {
        this.order = order;
        this.bucket = bucket;
    }

}

