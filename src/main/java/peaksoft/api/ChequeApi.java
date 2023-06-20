package peaksoft.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequePagination;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SumAllChequeOfDay;
import peaksoft.service.ChequeService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cheques")
@RequiredArgsConstructor
public class ChequeApi {

    private final ChequeService chequeService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @PostMapping("/{userId}/save")
    public SimpleResponse save(@PathVariable Long userId, @RequestBody ChequeRequest chequeRequest){
        return chequeService.saveCheque(userId, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER', 'CHEF')")
    @GetMapping("/{id}/getAll")
    public ChequePagination getAll(@PathVariable Long id,
                                   @RequestParam(required = false, defaultValue = "1") int page,
                                   @RequestParam(required = false, defaultValue = "4") int size) {
        return chequeService.getAll(id, PageRequest.of(page - 1, size));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{id}")
    public ChequeResponse getById(@PathVariable Long id){
        return chequeService.getChequeById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid ChequeRequest chequeRequest) {
        return chequeService.updateCheque(id, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id){
        return chequeService.deleteChequeById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/{userId}/sumChequeOfDay")
    public SumAllChequeOfDay sum(@PathVariable Long userId, @RequestParam LocalDate date) {
        return chequeService.sumAllChequeOfDay(userId, date);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/{userId}/avgChequeSum")
    public SimpleResponse avgCheque(@PathVariable Long userId, @RequestParam LocalDate date) {
        return chequeService.avgCheque(userId, date);
    }
}
