package com.Adoption_ms.services;


import com.Adoption_ms.data.Adoption;
import java.util.List;


public interface AdoptionService {
    Adoption saveAdoption(Adoption adoption);
    List<Adoption> getAllAdoptions();
    Adoption getAdoptionById(int id);
    Adoption updateAdoption(int id, Adoption adoption);
    void deleteAdoption(int id);
    List<Adoption> getAdoptionsByShelterId(int shelterId);

}