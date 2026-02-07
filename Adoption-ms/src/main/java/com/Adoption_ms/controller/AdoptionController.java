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
    // Better approach: Use an absolute path or a system property
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/pet/";
    public AdoptionController(AdoptionService service) {
        this.service = service;
    }

    // ---------------- Create Adoption ----------------
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Adoption createAdoption(
            @RequestPart("adoption") String adoptionJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Adoption adoption = mapper.readValue(adoptionJson, Adoption.class);

            // Ensure upload folder exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Save image if provided
            if (image != null && !image.isEmpty()) {
                // Prevent duplicate filenames by adding timestamp
                String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                File file = new File(uploadDir, filename);
                image.transferTo(file);

                // Store relative path for DB
                adoption.setImage_path("/uploads/pet/" + filename);
            }

            return service.saveAdoption(adoption);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse adoption JSON: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create adoption: " + e.getMessage());
        }
    }

    // ---------------- Get All Adoptions ----------------
    @GetMapping
    public List<Adoption> getAll() {
        return service.getAllAdoptions();
    }

    // ---------------- Get Adoption By ID ----------------
    @GetMapping("/{id}")
    public Adoption getById(@PathVariable int id) {
        Adoption adoption = service.getAdoptionById(id);
        if (adoption == null) throw new RuntimeException("Adoption ID not found: " + id);
        return adoption;
    }

    // ---------------- Update Adoption ----------------
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Adoption updateAdoption(
            @PathVariable int id,
            @RequestPart("adoption") String adoptionJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Adoption adoption = mapper.readValue(adoptionJson, Adoption.class);

            Adoption existing = service.getAdoptionById(id);
            if (existing == null) throw new RuntimeException("Adoption ID not found: " + id);

            // Update fields
            existing.setPet_name(adoption.getPet_name());
            existing.setBreed(adoption.getBreed());
            existing.setSpecies(adoption.getSpecies());
            existing.setAge(adoption.getAge());
            existing.setSize(adoption.getSize());
            existing.setShelterId(adoption.getShelterId());
            existing.setVaccinated(adoption.isVaccinated());
            existing.setKid_friendly(adoption.isKid_friendly());
            existing.setMedical_notes(adoption.getMedical_notes());
            existing.setSpecial_needs(adoption.getSpecial_needs());

            // Save new image if provided
            if (image != null && !image.isEmpty()) {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                File file = new File(uploadDir, filename);
                image.transferTo(file);

                existing.setImage_path("/uploads/pet/" + filename);
            }

            return service.saveAdoption(existing);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse adoption JSON: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update adoption: " + e.getMessage());
        }
    }

    // ---------------- Delete Adoption ----------------
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        Adoption adoption = service.getAdoptionById(id);
        if (adoption == null) throw new RuntimeException("Adoption ID not found: " + id);
        service.deleteAdoption(id);
        return "Deleted Successfully";
    }
    // ---------------- Get Adoptions By Shelter ID ----------------
    @GetMapping("/shelter/{shelterId}")
    public List<Adoption> getByShelterId(@PathVariable int shelterId) {
        return service.getAdoptionsByShelterId(shelterId);
    }

}
