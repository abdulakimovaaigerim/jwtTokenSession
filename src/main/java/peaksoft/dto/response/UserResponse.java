package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Builder
public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        ZonedDateTime createDate,
        ZonedDateTime updateDate,
        Role role
) {
}
