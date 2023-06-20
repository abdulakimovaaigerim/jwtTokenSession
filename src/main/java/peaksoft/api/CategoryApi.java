package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoriesResponse;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.pagination.PaginationResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/getAll")
    public List<CategoriesResponse> getAll() {
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryService.categorySubCategories(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id) {
        return categoryService.deleteCategoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/pagination")
    public PaginationResponse<CategoriesResponse> getCategoryPagination(
            @RequestParam(name = "page",required = false, defaultValue = "1") int page,
            @RequestParam(name = "size",required = false, defaultValue = "4") int size){
        return categoryService.getCategoryPagination(page, size);
    }
}
