package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.ProductResponse;
import peaksoft.entiti.Product;
import peaksoft.enums.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new peaksoft.dto.response.ProductResponse(p.id, p.name, p.images, p.characteristic, p.isFavorite, p.madeline, p.category) from Product p where p.id=:id")
    Optional<ProductResponse> getProductById(Long id);

    @Query("select new peaksoft.dto.response.ProductResponse(p.id, p.name, p.images, p.characteristic, p.isFavorite, p.madeline, p.category) FROM Product p WHERE p.brand.id = :brandId")
    List<ProductResponse> getAllProducts(@Param("brandId") Long brandId);

//    @Query("select new peaksoft.dto.response.ProductResponse(p.id, p.name, p.images, p.characteristic, p.price, p.isFavorite, p.madeline, p.category) from Product  p where  p.category=:category and p.price<=:price order by p.price asc")
//    List<ProductResponse> findByCategoryAndPriceLessThanEqualOrderByPriceAsc(@Param("category") Category category, @Param("price") BigDecimal price);


    @Query("select count(p.isFavorite) from Product p where p.id=:productId")
    int countLike(Long productId);
}
