package ozone.com.eproz.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import ozone.com.eproz.model.TrainingModel;

public record TrainingDtoResponse(
        UUID id,
        String title,
        String description,
        String provider,
        String attachment,
        LocalDate startDate,
        LocalDate endDate,
        UUID staffId,
        Instant createdAt,
        Instant updatedAt
) {
    public static TrainingDtoResponse from(TrainingModel training) {
        return new TrainingDtoResponse(
                training.getId(),
                training.getTitle(),
                training.getDescription(),
                training.getProvider(),
                training.getAttachment(),
                training.getStartDate(),
                training.getEndDate(),
                training.getStaff() != null ? training.getStaff().getId() : null,
                training.getCreatedAt(),
                training.getUpdatedAt()
        );
    }
}