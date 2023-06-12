package peaksoft.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthUserRequest(
        @NotBlank(message = "email cannot be empty!")
        @Email(message = "Invalid email!")
        String email,

        @Size(min = 4, max = 100, message = "Password must be at least 4 characters!")
        @NotBlank(message = "password cannot be empty!")
        String password ) {
}
