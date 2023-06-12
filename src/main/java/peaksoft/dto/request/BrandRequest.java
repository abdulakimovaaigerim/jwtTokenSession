package peaksoft.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BrandRequest(
        @NotBlank(message = "brandName cannot be empty!")
        String brandName,
        @NotBlank(message = "image cannot be empty!")
        String image
) {
}
