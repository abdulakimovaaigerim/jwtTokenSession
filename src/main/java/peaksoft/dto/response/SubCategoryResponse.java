package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record SubCategoryResponse(
        String categoryName,
        Long id,
        String name
) {
}
