package dev.zvaryyka.nikestorebackend.repositories;

import dev.zvaryyka.nikestorebackend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer>
{
    Optional<Person> findByEmail(String email);

    Boolean existsByEmail(String email);




}
