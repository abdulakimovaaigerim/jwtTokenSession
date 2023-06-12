package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.BasketResponse;
import peaksoft.entiti.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {

@Query("select new peaksoft.dto.response.BasketResponse(b.id) from Basket b where b.id=:id")
    Optional<BasketResponse> getBasketById(@Param("id") Long id);

    @Query("select new peaksoft.dto.response.BasketResponse(b.id) from Basket b where b.user.id=:userId")
    List<BasketResponse> getAllBaskets(Long userId);
}
