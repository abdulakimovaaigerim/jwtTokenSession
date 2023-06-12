package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record BrandResponse(
        Long id,
        String brandName,
        String image
) {
}
