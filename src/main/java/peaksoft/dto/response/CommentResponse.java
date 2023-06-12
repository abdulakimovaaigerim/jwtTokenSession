package peaksoft.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CommentResponse(
        Long id,
        String comment,
        LocalDate createAt
) {
}
