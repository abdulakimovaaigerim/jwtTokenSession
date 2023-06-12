package peaksoft.service;

import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.enums.Category;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    SimpleResponse createProduct(Long brandId, ProductRequest productRequest);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts(Long brandId);

    SimpleResponse updateProduct(Long id, ProductRequest productRequest);

    SimpleResponse deleteProduct(Long id);

    SimpleResponse assignBasketToProduct(Long productId, Long basketId);
//    List<ProductResponse> filterProducts(Category category, BigDecimal price);
    int countLike(Long productId);


    }
