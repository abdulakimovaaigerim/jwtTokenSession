package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.Role;

import java.time.LocalDate;

@Builder
public record UserResponse (
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        Role role,
        int experience
) {
}
