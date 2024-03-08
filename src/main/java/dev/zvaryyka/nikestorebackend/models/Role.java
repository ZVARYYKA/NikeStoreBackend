package dev.zvaryyka.nikestorebackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    public Role(String name) {
        this.name = ERole.valueOf(name);
    }
}
