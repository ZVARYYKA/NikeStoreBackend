package dev.zvaryyka.nikestorebackend.services;

import dev.zvaryyka.nikestorebackend.models.Person;
import dev.zvaryyka.nikestorebackend.repositories.PeopleRepository;
import dev.zvaryyka.nikestorebackend.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public UserDetailsServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = peopleRepository.
                findByEmail(email).orElseThrow(()
                        -> new UsernameNotFoundException
                        ("User Not found with email: " + email));

        return UserDetailsImpl.build(person);
    }
}
