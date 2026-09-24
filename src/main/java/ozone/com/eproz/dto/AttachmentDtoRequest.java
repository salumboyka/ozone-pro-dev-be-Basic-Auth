package ozone.com.eproz.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public record AttachmentDtoRequest(
        @NotNull(message = "Attachment file is required")
        MultipartFile file
) {
}