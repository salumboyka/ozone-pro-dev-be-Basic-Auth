package ozone.com.eproz.dto;

import java.time.Instant;
import java.util.UUID;

import ozone.com.eproz.enums.GenderEnum;
import ozone.com.eproz.model.StaffModel;

public record StaffDtoResponse(
    UUID id,
    String email,
    String fname,
    String mname,
    String lname,
    GenderEnum gender,
    int age,
    String address,
    Instant createdAt,
    Instant updatedAt
) {
    public static StaffDtoResponse from(StaffModel u) {
        return new StaffDtoResponse(
            u.getId(),
            u.getEmail(),
            u.getFname(),
            u.getMname(),
            u.getLname(),
            u.getGender(),
            u.getAge(),
            u.getAddress(),
            u.getCreatedAt(),
            u.getUpdatedAt()
        );
    }
}