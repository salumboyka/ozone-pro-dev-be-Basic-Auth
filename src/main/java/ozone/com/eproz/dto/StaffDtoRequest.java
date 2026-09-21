package ozone.com.eproz.dto;
import jakarta.validation.constraints.*;
import ozone.com.eproz.enums.GenderEnum;

public record StaffDtoRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,

    @NotBlank(message = "First name is required")
    @Size(max = 120, message = "First name must be less than or equal to 120 characters")
    String fname,

    @NotBlank(message = "Middle name is required")
    @Size(max = 120, message = "Middle name must be less than or equal to 120 characters")
    String mname,

    @NotBlank(message = "Last name is required")
    @Size(max = 120, message = "Last name must be less than or equal to 120 characters")
    String lname,

    @NotNull(message = "Gender is required")
    GenderEnum gender,

    @Min(value = 0, message = "Age must be greater than or equal to 0")
    int age,

    @NotBlank(message = "Address is required")
    @Size(max = 120, message = "Address must be less than or equal to 120 characters")
    String address
) {
}