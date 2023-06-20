package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.entities.Restaurant;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;

    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest request) {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        if (restaurants.size() > 0) {
            throw new BadCredentialException("Only one restaurant will be!!!");

        }

        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .location(request.location())
                .restType(request.resType())
                .service(request.service())
                .build();
        restaurantRepository.save(restaurant);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with id: " + restaurant.getName() + " is saved!"))
                .build();
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

    @Override
    public RestaurantResponse getByIdRestaurant(Long id) {
        return restaurantRepository.getRestaurantById(id).orElseThrow(() ->
                new NotFoundException("Restaurant with id: " + id + " is not found!"));
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Restaurant with id: "  + id + " is not found!"));

        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setRestType(request.resType());
        restaurant.setService(request.service());

        restaurantRepository.save(restaurant);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with id: " + restaurant.getName() + " is updated!"))
                .build();
    }

    @Override
    public SimpleResponse deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new NotFoundException("Restaurant with id: " + id + " doesn't exists!");
        }
        restaurantRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with id: " + id + " is deleted!"))
                .build();
    }

}
