package peaksoft.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CommentRequest(
        @NotBlank(message = "comment cannot be empty!")
        String comment,
        @NotBlank(message = "createAt cannot be empty!")
        LocalDate createAt
) {
}
