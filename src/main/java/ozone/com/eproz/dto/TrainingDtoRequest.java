package ozone.com.eproz.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TrainingDtoRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title must be less than or equal to 150 characters")
        String title,

        @Size(max = 500, message = "Description must be less than or equal to 500 characters")
        String description,

        @NotBlank(message = "Provider is required")
        @Size(max = 150, message = "Provider must be less than or equal to 150 characters")
        String provider,

        @Size(max = 255, message = "Attachment must be less than or equal to 255 characters")
        String attachment,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate,

        @NotNull(message = "Staff id is required")
        UUID staffId,

        MultipartFile attachmentFile
) {
}