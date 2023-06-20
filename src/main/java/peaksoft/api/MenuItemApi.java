package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.pagination.PaginationResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/menuItems")
@RequiredArgsConstructor
public class MenuItemApi {

    private final MenuItemService menuItemService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/{restId}/{subCategoryId}/save")
    public SimpleResponse save(@PathVariable Long restId, @PathVariable Long subCategoryId, @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.saveMenuItem(restId, subCategoryId, menuItemRequest);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{subCategoryId}/getAll")
    public List<MenuItemResponse> getAll(@PathVariable Long subCategoryId,
                                         @RequestParam(required = false) String word) {
        return menuItemService.getAllMenuItems(subCategoryId, word);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{menuItemId}")
    public MenuItemResponse getById(@PathVariable Long menuItemId) {
        return menuItemService.getByIdMenuItem(menuItemId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid MenuItemRequest menuItemRequest) {
        return menuItemService.updateMenuItem(id, menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id) {
        return menuItemService.deleteMenuItemById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/sort")
    public List<MenuItemResponse> sort(@RequestParam(required = false) String ascOrDesc) {
        return menuItemService.sortMenuItem(ascOrDesc);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/vegetarian")
    List<MenuItemResponse> isVegetarian(@RequestParam boolean isTrue) {
        return menuItemService.isVegetarian(isTrue);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/pagination")
    PaginationResponse<MenuItemResponse> pagination(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "4") int size) {
        return menuItemService.getMenuItemPagination(PageRequest.of(page - 1, size));
    }
}
