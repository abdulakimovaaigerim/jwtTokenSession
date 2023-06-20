package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entities.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id, r.name, r.location, r.restType, r.numberOfEmployees, r.service) from Restaurant r")
    List<RestaurantResponse> getAllRestaurants();

    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id, r.name, r.location, r.restType, r.numberOfEmployees, r.service) from Restaurant r where r.id=:id")
   Optional<RestaurantResponse> getRestaurantById(@Param("id") Long id);
}
