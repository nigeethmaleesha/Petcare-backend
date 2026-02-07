package com.donation_ms.service;

// FileUploadService.java

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    private final Path uploadPath = Paths.get("uploads");

    public FileUploadService() {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public String uploadImage(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String filename = UUID.randomUUID().toString() + fileExtension;
            Path destinationFile = uploadPath.resolve(filename).normalize().toAbsolutePath();

            // Copy file to upload directory
            Files.copy(file.getInputStream(), destinationFile);

            // Return the URL where the image can be accessed
            return "http://localhost:8080/uploads/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}
