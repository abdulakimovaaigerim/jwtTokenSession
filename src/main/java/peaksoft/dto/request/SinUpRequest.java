package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record SinUpRequest(
        String email,
        String password
) {
}
