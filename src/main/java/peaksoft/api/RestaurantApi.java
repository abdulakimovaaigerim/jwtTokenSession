package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @PostMapping("/save")
    public SimpleResponse save(@RequestBody RestaurantRequest request){
        return restaurantService.saveRestaurant(request);
    }

    @GetMapping("/getAll")
    public List<RestaurantResponse> getAll(){
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantResponse getById(@PathVariable Long id){
        return restaurantService.getByIdRestaurant(id);
    }

    @PutMapping("/{id}/update")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid RestaurantRequest request){
        return restaurantService.updateRestaurant(id, request);
    }

    @DeleteMapping("/{id}/delete")
    public SimpleResponse delete(@PathVariable Long id){
        return restaurantService.deleteRestaurant(id);
    }

}
