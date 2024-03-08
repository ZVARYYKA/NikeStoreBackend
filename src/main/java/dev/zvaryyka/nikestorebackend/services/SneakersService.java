package dev.zvaryyka.nikestorebackend.services;

import dev.zvaryyka.nikestorebackend.models.Sneakers;
import dev.zvaryyka.nikestorebackend.repositories.SneakersRepository;
import dev.zvaryyka.nikestorebackend.util.sneakers.SneakersNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SneakersService {

    private final SneakersRepository sneakersRepository;

    @Autowired
    public SneakersService(SneakersRepository sneakersRepository) {
        this.sneakersRepository = sneakersRepository;
    }
    public List<Sneakers> getAllSneakers() {
        return sneakersRepository.findAll();
    }
    public Sneakers getOneSneakers(int id) {
        Optional<Sneakers> foundSneakers = sneakersRepository.findById(id);
        return foundSneakers.orElseThrow(SneakersNotFoundException::new);
    }
    @Transactional
    public void saveNewSneakers(Sneakers sneakers) {
        sneakersRepository.save(sneakers);
    }
    @Transactional
    public void updateSneakers(int id,Sneakers sneakers) {
        Optional<Sneakers> optionalUpdateSneakers = sneakersRepository.findById(id);
        Sneakers updateSneakers = optionalUpdateSneakers.get();
        updateSneakers.setName(sneakers.getName());
        updateSneakers.setPrice(sneakers.getPrice());
        updateSneakers.setAbout(sneakers.getAbout());
        updateSneakers.setImagePath(sneakers.getImagePath());
        sneakersRepository.save(updateSneakers);

    }
    @Transactional
    public void deleteSneakers(int id) {
        sneakersRepository.deleteById(id);
    }
}
