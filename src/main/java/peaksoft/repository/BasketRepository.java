package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.BasketResponse;
import peaksoft.entiti.Basket;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query("select new peaksoft.dto.response.BasketResponse(b.id, b.user) from Basket b")
    List<BasketResponse> getAllBasket();
}
