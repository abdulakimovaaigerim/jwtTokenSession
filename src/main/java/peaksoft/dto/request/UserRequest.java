package peaksoft.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Builder
public record UserRequest(
        @NotBlank(message = "First-Name cannot be empty!")
        String firstName,
        @NotBlank(message = "Last-Name cannot be empty!")
        String lastName,
        @NotBlank(message = "email cannot be empty!")
        @Email(message = "Invalid email!")
        String email,
        @Size(min = 5, max = 100, message = "Password must be at least 4 characters!")
        @NotBlank(message = "password cannot be empty!")
        String password

) {
}
