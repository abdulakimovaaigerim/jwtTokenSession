package peaksoft.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FavoriteRequest {
    @NotBlank(message = "userId cannot be empty!")
    private Long userId;
    @NotBlank(message = "productId cannot be empty!")
    private Long productId;
}
