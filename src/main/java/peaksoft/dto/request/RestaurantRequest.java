package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record RestaurantRequest(
        String name,
        String location,
        String resType,
        int service
) {
}
