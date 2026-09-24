package ozone.com.eproz.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NextOfKinDtoRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 120, message = "Full name must be less than or equal to 120 characters")
        String fullName,

        @NotBlank(message = "Relationship is required")
        @Size(max = 50, message = "Relationship must be less than or equal to 50 characters")
        String relationship,

        @NotBlank(message = "Phone number is required")
        @Size(max = 30, message = "Phone number must be less than or equal to 30 characters")
        String phoneNumber,

        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must be less than or equal to 100 characters")
        String email,

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must be less than or equal to 255 characters")
        String address,

        @NotNull(message = "Staff id is required")
        UUID staffId
) {
}
