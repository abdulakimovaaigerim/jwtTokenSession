package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopLists")
@RequiredArgsConstructor
public class StopListApi {
    private final StopListService stopListService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/{menuItemId}/save")
    public SimpleResponse save(@PathVariable Long menuItemId, @RequestBody @Valid StopListRequest stopListRequest) {
        return stopListService.saveStopList(menuItemId, stopListRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{menuItemId}/getAll")
    public List<StopListResponse> getAll(@PathVariable Long menuItemId) {
        return stopListService.getAllStopLists(menuItemId);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{id}")
    public StopListResponse getById(@PathVariable Long id) {
        return stopListService.findByIdStopList(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid StopListRequest stopListRequest) {
        return stopListService.updateStopList(id, stopListRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id) {
        return stopListService.deleteByIdStopList(id);
    }
}

