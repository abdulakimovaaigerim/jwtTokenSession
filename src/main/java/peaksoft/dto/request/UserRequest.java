package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record UserRequest(

        String email,
        String password
) {
}
