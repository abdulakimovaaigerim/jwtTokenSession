package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.FavoriteRequest;
import peaksoft.dto.response.FavoriteResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteApi {

    private final FavoriteService favoriteService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid FavoriteRequest favoriteRequest) {
        return favoriteService.saveFavorite(favoriteRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public FavoriteResponse getById(@PathVariable Long id) {
        return favoriteService.getFavoriteById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{productId}/{userId}/getAll")
    public List<FavoriteResponse> getAll(@PathVariable Long productId, @PathVariable Long userId) {
        return favoriteService.getAllFavorite(productId, userId);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid FavoriteRequest favoriteRequest) {
        return favoriteService.updateFavorite(id, favoriteRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id) {
        return favoriteService.deleteFavorite(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/{productId}/added")
    public SimpleResponse added(@PathVariable Long userId, @PathVariable Long productId){
        return favoriteService.addProductToFavorites(userId, productId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}/{productId}/delete")
    public SimpleResponse delete(@PathVariable Long userId, @PathVariable Long productId){
        return favoriteService.removeProductFromFavorites(userId, productId);
    }
}
