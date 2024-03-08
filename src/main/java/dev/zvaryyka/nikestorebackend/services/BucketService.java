package dev.zvaryyka.nikestorebackend.services;

import dev.zvaryyka.nikestorebackend.models.Bucket;
import dev.zvaryyka.nikestorebackend.models.Person;
import dev.zvaryyka.nikestorebackend.repositories.BucketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {
    private final BucketsRepository bucketsRepository;
    @Autowired
    public BucketService(BucketsRepository bucketsRepository) {
        this.bucketsRepository = bucketsRepository;
    }
    public List<Bucket> findAllByUser(Person person) {
        return bucketsRepository.findAllByUser(person);
    }
    public List<Bucket> findAllByUserAndStatusFalse(Person person) {
        return bucketsRepository.findAllByUserAndStatusFalse(person);
    }
    public void save(Bucket bucket) {
        bucketsRepository.save(bucket);
    }

    public void deleteById(int bucketId) {
        bucketsRepository.deleteById(bucketId);
    }
}
