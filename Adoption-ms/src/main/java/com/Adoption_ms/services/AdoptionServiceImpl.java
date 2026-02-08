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

    private synchronized String generateNextId() {
        List<String> ids = repository.findLastAdoptionId();

        if (ids.isEmpty() || !ids.get(0).startsWith("PET-")) {
            return "PET-001";
        }

        int lastNum = Integer.parseInt(ids.get(0).split("-")[1]);
        return String.format("PET-%03d", lastNum + 1);
    }

    @Override
    public Adoption saveAdoption(Adoption adoption) {
        if (adoption.getAdoption_id() == null || adoption.getAdoption_id().isEmpty()) {
            adoption.setAdoption_id(generateNextId());
        }
        return repository.save(adoption);
    }

    @Override
    public List<Adoption> getAllAdoptions() {
        return repository.findAll();
    }

    @Override
    public Adoption getAdoptionById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteAdoption(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Adoption> getAdoptionsByShelterId(String shelterId) {
        return repository.findByShelterId(shelterId);
    }
}
