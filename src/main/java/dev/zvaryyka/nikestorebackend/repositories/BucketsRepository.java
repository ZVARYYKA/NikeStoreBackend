package dev.zvaryyka.nikestorebackend.repositories;

import dev.zvaryyka.nikestorebackend.models.Bucket;
import dev.zvaryyka.nikestorebackend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BucketsRepository extends JpaRepository<Bucket,Integer> {
    List<Bucket> findAllByUser(Person person);

    List<Bucket> findByUser(Person user);

    List<Bucket> findAllByUserAndStatusFalse(Person person);
}
