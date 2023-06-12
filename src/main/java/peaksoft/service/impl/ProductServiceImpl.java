package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entiti.Basket;
import peaksoft.entiti.Brand;
import peaksoft.entiti.Product;
import peaksoft.entiti.User;
import peaksoft.enums.Category;
import peaksoft.exception.ConflictException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.BasketRepository;
import peaksoft.repository.BrandRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new NotFoundException("User not found!")
        );
    }

    @Override
    public SimpleResponse createProduct(Long brandId, ProductRequest productRequest) {

        Brand brand = brandRepository.findById(brandId).orElseThrow(() ->
                new NotFoundException("Brand with id: " + brandId + " is not found!"));

        Product product = new Product();
        product.setName(productRequest.name());
        product.setImages(productRequest.images());
        product.setCharacteristic(productRequest.characteristic());
        product.setIsFavorite(productRequest.isFavorite());
        product.setMadeline(productRequest.madeline());
        product.setCategory(productRequest.category());

        product.setBrand(brand);
        productRepository.save(product);
        brand.addProduct(product);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Product with id: " + product.getName() + " is saved"))
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {
//        User authentication = getAuthentication();
//        Product product = productRepository.findById(id).orElseThrow(() ->
//                new NotFoundException("Product with id: " + id + " is not found!"));

        return productRepository.getProductById(id).orElseThrow(()->
                new NotFoundException("Product with id: " + id + " is not found!"));
//        return ProductResponse.builder()
//                .id(product.getId())
//                .name(product.getName())
//                .images(product.getImages())
//                .characteristic(product.getCharacteristic())
//                .isFavorite(product.getIsFavorite())
//                .madeline(product.getMadeline())
//                .category(product.getCategory())
//                .build();
    }

    @Override
    public List<ProductResponse> getAllProducts(Long brandId) {
        return productRepository.getAllProducts(brandId);
    }

    @Override
    public SimpleResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Product with id: " + id + " is not found!"));

        product.setName(productRequest.name());
        product.setImages(productRequest.images());
        product.setCharacteristic(productRequest.characteristic());
        product.setIsFavorite(productRequest.isFavorite());
        product.setMadeline(productRequest.madeline());
        product.setCategory(productRequest.category());

        productRepository.save(product);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Product with id: " + product.getName() + " is updated"))
                .build();
    }

    @Override
    public SimpleResponse deleteProduct(Long id) {
        productRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Product with id: " + id + " is deleted"))
                .build();
    }

    @Override
    public SimpleResponse assignBasketToProduct(Long productId, Long basketId) {
        Product product = productRepository.findById(basketId).orElseThrow(() ->
                new NotFoundException("Product with id: " + productId + " is not found!"));
        Basket basket = basketRepository.findById(basketId).orElseThrow(() ->
                new NotFoundException("Basket with id: " + basketId + " is not found!"));

        if (basket.getUser() != null) {
            for (Basket productBasket : product.getBaskets()) {
                if (productBasket.getId().equals(productId)) {
                    throw new ConflictException("This group already exists!");

                }
            }
            product.addBasket(basket);
            basket.addProduct(product);
            basketRepository.save(basket);
            productRepository.save(product);
        }

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Product with id : " + productId + "  " + "Basket with id: " + basketId + " successfully assign"))
                .build();
    }

//    @Override
//    public List<ProductResponse> filterProducts(Category category, BigDecimal price) {
//        return productRepository.findByCategoryAndPriceLessThanEqualOrderByPriceAsc(category, price);
//    }

    @Override
    public int countLike(Long productId) {
        return productRepository.countLike(productId);
    }


}