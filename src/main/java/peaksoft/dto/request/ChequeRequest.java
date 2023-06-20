package peaksoft.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record ChequeRequest(
        List<Long> menuItemsId

) {
}
