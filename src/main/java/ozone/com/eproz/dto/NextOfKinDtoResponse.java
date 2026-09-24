package ozone.com.eproz.dto;

import java.time.Instant;
import java.util.UUID;

import ozone.com.eproz.model.NextOfKinModel;

public record NextOfKinDtoResponse(
        UUID id,
        String fullName,
        String relationship,
        String phoneNumber,
        String email,
        String address,
        UUID staffId,
        Instant createdAt,
        Instant updatedAt
) {
    public static NextOfKinDtoResponse from(NextOfKinModel nextOfKin) {
        return new NextOfKinDtoResponse(
                nextOfKin.getId(),
                nextOfKin.getFullName(),
                nextOfKin.getRelationship(),
                nextOfKin.getPhoneNumber(),
                nextOfKin.getEmail(),
                nextOfKin.getAddress(),
                nextOfKin.getStaff() != null ? nextOfKin.getStaff().getId() : null,
                nextOfKin.getCreatedAt(),
                nextOfKin.getUpdatedAt()
        );
    }
}
