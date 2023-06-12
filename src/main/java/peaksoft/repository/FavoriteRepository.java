package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.FavoriteResponse;
import peaksoft.entiti.Favorite;

import java.util.List;
import java.util.Optional;


public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("SELECT new peaksoft.dto.response.FavoriteResponse(f.id) FROM Favorite f WHERE f.product.id = :productId AND f.user.id = :userId")
    List<FavoriteResponse> getAllFavorites(Long productId, Long userId);

    @Query("select new peaksoft.dto.response.FavoriteResponse(f.id) from Favorite f where f.id=:favoriteId")
    Optional<FavoriteResponse> getByFavoriteId(@Param("favoriteId") Long favoriteId);

    @Query("select new peaksoft.dto.response.FavoriteResponse(f.id) from Favorite f where f.user.id=:userId and f.product.id=:productId")
    Optional<FavoriteResponse> findByUserIdAndProductId(Long userId, Long productId);

}
