package peaksoft.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ChequePagination(
        List<ChequeResponse> cheques,
        int currentPage,
        int totalPages

) {
}
