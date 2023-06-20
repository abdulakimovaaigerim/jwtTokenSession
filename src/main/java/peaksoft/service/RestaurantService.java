package peaksoft.service;

import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {

    SimpleResponse saveRestaurant(RestaurantRequest request);

    List<RestaurantResponse> getAllRestaurants();

    RestaurantResponse getByIdRestaurant(Long id);

    SimpleResponse updateRestaurant(Long id, RestaurantRequest request);

    SimpleResponse deleteRestaurant(Long id);

}
