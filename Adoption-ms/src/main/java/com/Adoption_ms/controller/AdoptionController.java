package com.Adoption_ms.controller;

import com.Adoption_ms.data.Adoption;
import com.Adoption_ms.services.AdoptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/adoptions")
@CrossOrigin
public class AdoptionController {

    private final AdoptionService service;
    private final String UPLOAD_DIR =
            System.getProperty("user.dir") + "/uploads/pet/";

    public AdoptionController(AdoptionService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Adoption createAdoption(
            @RequestPart("adoption") String adoptionJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Adoption adoption = mapper.readValue(adoptionJson, Adoption.class);

        handleImageUpload(adoption, image);
        return service.saveAdoption(adoption);
    }

    @GetMapping
    public List<Adoption> getAll() {
        return service.getAllAdoptions();
    }

    @GetMapping("/{id}")
    public Adoption getById(@PathVariable String id) {
        Adoption adoption = service.getAdoptionById(id);
        if (adoption == null) {
            throw new RuntimeException("Adoption not found: " + id);
        }
        return adoption;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Adoption updateAdoption(
            @PathVariable String id,
            @RequestPart("adoption") String adoptionJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Adoption updated = mapper.readValue(adoptionJson, Adoption.class);

        Adoption existing = service.getAdoptionById(id);
        if (existing == null) {
            throw new RuntimeException("Not found: " + id);
        }

        existing.setPet_name(updated.getPet_name());
        existing.setBreed(updated.getBreed());
        existing.setSpecies(updated.getSpecies());
        existing.setAge(updated.getAge());
        existing.setSize(updated.getSize());
        existing.setShelterId(updated.getShelterId());
        existing.setVaccinated(updated.isVaccinated());
        existing.setKid_friendly(updated.isKid_friendly());
        existing.setMedical_notes(updated.getMedical_notes());
        existing.setSpecial_needs(updated.getSpecial_needs());

        handleImageUpload(existing, image);
        return service.saveAdoption(existing);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.deleteAdoption(id);
        return "Deleted Successfully";
    }

    @GetMapping("/shelter/{shelterId}")
    public List<Adoption> getByShelterId(@PathVariable String shelterId) {
        return service.getAdoptionsByShelterId(shelterId);
    }

    private void handleImageUpload(Adoption adoption, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            File file = new File(uploadDir, filename);
            image.transferTo(file);

            adoption.setImage_path("/uploads/pet/" + filename);
        }
    }
}
