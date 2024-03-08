package dev.zvaryyka.nikestorebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "email",unique = true)
    private String email;


    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Bucket> buckets;

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Order> orders;

    public Person(String email,String password) {
        this.email = email;
        this.password = password;
    }

    public Person(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
}
