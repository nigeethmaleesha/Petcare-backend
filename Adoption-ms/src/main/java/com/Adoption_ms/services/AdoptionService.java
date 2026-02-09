package com.Adoption_ms.services;

import com.Adoption_ms.data.Adoption;
import java.util.List;

public interface AdoptionService {

    Adoption saveAdoption(Adoption adoption);

    List<Adoption> getAllAdoptions();

    Adoption getAdoptionById(String id);

    void deleteAdoption(String id);

    List<Adoption> getAdoptionsByShelterId(String shelterId);
}
