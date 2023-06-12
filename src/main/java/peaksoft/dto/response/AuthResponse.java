package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String email,
        String token
) {
}
