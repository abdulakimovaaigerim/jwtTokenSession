package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.BasketService;

import java.util.List;


@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketApi {

    private final BasketService basketService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid BasketRequest basketRequest) {
        return basketService.saveBasket(basketRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public BasketResponse getById(@PathVariable Long id) {
        return basketService.getByBasketId(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}/getAll")
    public List<BasketResponse> getAll(@PathVariable Long userId) {
        return basketService.getAllBaskets(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid BasketRequest basketRequest) {
        return basketService.updateBasket(id, basketRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id) {
        return basketService.deleteById(id);
    }
}
