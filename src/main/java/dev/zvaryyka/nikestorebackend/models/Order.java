package dev.zvaryyka.nikestorebackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Person user;

    @Column(name = "address")
    private String address;

    @Column(name = "about")
    private String about;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetailsList;

    public Order(Person user, String address, String about) {
        this.user = user;
        this.address = address;
        this.about = about;
    }
}
