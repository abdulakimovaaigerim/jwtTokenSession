package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subCategories")
@RequiredArgsConstructor
public class SubCategoryApi {
    private final SubCategoryService service;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/{id}/save")
    public SimpleResponse save(@PathVariable Long id, @RequestBody @Valid SubCategoryRequest subCategoryRequest) {
        return service.saveSubCategory(id, subCategoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{id}/getAll")
    public List<SubCategoryResponse> getAll(@PathVariable Long id) {
        return service.getAllByCategoryId(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{id}")
    public SubCategoryResponse getById(@PathVariable Long id) {
        return service.findByIdSubCategory(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid SubCategoryRequest subCategoryRequest) {
        return service.updateSubCategory(id, subCategoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id) {
        return service.deleteSubCategoryById(id);
    }

    @GetMapping("/grouping")
    public Map<String,List<SubCategoryResponse>> filter(){
        return service.filterByCategory();
    }
}
