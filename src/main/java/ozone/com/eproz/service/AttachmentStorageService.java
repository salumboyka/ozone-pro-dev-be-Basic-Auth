package ozone.com.eproz.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

import ozone.com.eproz.dto.AttachmentDtoResponse;
import ozone.com.eproz.exception.BusinessRuleException;

@Service
public class AttachmentStorageService {

    private final String uploadDirectory;

    public AttachmentStorageService(
            @Value("${app.attachments.directory:uploads/attachments}") String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    @PostConstruct
    void createUploadDirectory() {
        try {
            Files.createDirectories(Paths.get(uploadDirectory).toAbsolutePath().normalize());
        } catch (IOException ex) {
            throw new BusinessRuleException("Attachment directory could not be created");
        }
    }

    public AttachmentDtoResponse store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessRuleException("Attachment file is required");
        }

        String originalFileName = file.getOriginalFilename() == null
                ? "attachment"
                : Paths.get(file.getOriginalFilename()).getFileName().toString();
        String storedFileName = UUID.randomUUID() + getExtension(originalFileName);
        Path directory = Paths.get(uploadDirectory).toAbsolutePath().normalize();
        Path destination = directory.resolve(storedFileName).normalize();

        try {
            Files.createDirectories(directory);
            file.transferTo(destination);
        } catch (IOException ex) {
            throw new BusinessRuleException("Attachment could not be stored");
        }

        String storedPath = "attachments/" + storedFileName;
        return new AttachmentDtoResponse(
                originalFileName,
                storedPath,
                file.getContentType(),
                file.getSize()
        );
    }

    private String getExtension(String filename) {
        int extensionStart = filename.lastIndexOf('.');
        return extensionStart >= 0 ? filename.substring(extensionStart).toLowerCase() : "";
    }
}