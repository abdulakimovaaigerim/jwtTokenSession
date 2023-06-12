package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.response.BrandResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandApi {

    private final BrandService brandService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public SimpleResponse saveBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return brandService.saveBrand(brandRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public BrandResponse getById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public List<BrandResponse> getAll() {
        return brandService.getAllBrands();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid BrandRequest brandRequest) {
        return brandService.updateBrand(id, brandRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse deleteById(@PathVariable Long id) {
        return brandService.deleteById(id);
    }
}
