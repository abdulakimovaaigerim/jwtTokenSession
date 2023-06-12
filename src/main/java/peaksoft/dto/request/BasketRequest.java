package peaksoft.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketRequest {
    @NotEmpty(message = "userId cannot be empty!")
    private Long userId;
}
