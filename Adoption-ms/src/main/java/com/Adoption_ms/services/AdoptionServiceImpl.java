package com.Adoption_ms.services;


import com.Adoption_ms.data.Adoption;
import com.Adoption_ms.data.AdoptionRepository;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class AdoptionServiceImpl implements AdoptionService {


    private final AdoptionRepository repository;


    public AdoptionServiceImpl(AdoptionRepository repository) {
        this.repository = repository;
    }


    public Adoption saveAdoption(Adoption adoption) {
        return repository.save(adoption);
    }


    public List<Adoption> getAllAdoptions() {
        return repository.findAll();
    }public Adoption getAdoptionById(int id) {
        return repository.findById(id).orElse(null);
    }


    public Adoption updateAdoption(int id, Adoption adoption) {
        Adoption existing = getAdoptionById(id);
        if (existing != null) {
            adoption.setAdoption_id(id);
            return repository.save(adoption);
        }
        return null;
    }


    public void deleteAdoption(int id) {
        repository.deleteById(id);
    }
    @Override
    public List<Adoption> getAdoptionsByShelterId(int shelterId) {
        return repository.findByShelterId(shelterId);
    }

}