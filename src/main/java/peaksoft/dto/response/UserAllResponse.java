package peaksoft.dto.response;

import peaksoft.enums.Role;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public record UserAllResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        ZonedDateTime createDate,
        ZonedDateTime updateDate,
        Role role

) {
}
