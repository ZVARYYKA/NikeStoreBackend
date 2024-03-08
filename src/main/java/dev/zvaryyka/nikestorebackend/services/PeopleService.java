package dev.zvaryyka.nikestorebackend.services;

import dev.zvaryyka.nikestorebackend.models.Person;
import dev.zvaryyka.nikestorebackend.repositories.PeopleRepository;
import dev.zvaryyka.nikestorebackend.util.person.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Person findByEmail(String name) {
        Optional<Person> foundPerson = peopleRepository.findByEmail(name);
        return foundPerson.orElseThrow(PersonNotFoundException::new);

    }
}
