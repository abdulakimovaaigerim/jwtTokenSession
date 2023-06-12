package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{brandId}/save")
    public SimpleResponse save(@PathVariable Long brandId, @RequestBody @Valid ProductRequest productRequest) {
        return productService.createProduct(brandId, productRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{brandId}/getAll")
    public List<ProductResponse> getAll(@PathVariable Long brandId) {
        return productService.getAllProducts(brandId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse deleteById(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{productId}/{basketId}/assign")
    public SimpleResponse assign(@PathVariable Long productId, @PathVariable Long basketId) {
        return productService.assignBasketToProduct(productId, basketId);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/filter")
//    public List<ProductResponse> filterProducts(@RequestParam("category")Category category,
//                                                @RequestParam("price") BigDecimal price) {
//        return productService.filterProducts(category, price);
//    }

    @GetMapping("/{id}/count")
    public int count(@PathVariable Long id){
        return productService.countLike(id);
    }
}
