package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entiti.Basket;
import peaksoft.entiti.User;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.BasketRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.BasketService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    private final UserRepository userRepository;

    @Override
    public SimpleResponse saveBasket(BasketRequest basketRequest) {
        User user = userRepository.findById(basketRequest.getUserId()).orElseThrow(() ->
                new NotFoundException("User with id: " + basketRequest.getUserId() + " is not found!"));
        Basket basket = new Basket();
        basket.setUser(user);
        basketRepository.save(basket);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Basket with id: " + basket.getId() + " is saved"))
                .build();
    }

    @Override
    public BasketResponse getByBasketId(Long id) {
        return basketRepository.getBasketById(id).orElseThrow(() ->
                new NotFoundException("Basket with id: " + id + " is not found!"));
    }

    @Override
    public List<BasketResponse> getAllBaskets(Long userId) {
        return basketRepository.getAllBaskets(userId);
    }

    @Override
    public SimpleResponse updateBasket(Long id, BasketRequest basketRequest) {
        Basket basket = basketRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Basket with id: " + id + " is not found!"));
        User user = userRepository.findById(basketRequest.getUserId()).orElseThrow(() ->
                new NotFoundException("User with id: " + basketRequest.getUserId() + " is not found!"));

        basket.setUser(user);
        basketRepository.save(basket);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Basket with id: " + basket.getId() + " is updated"))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        basketRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Basket with id: " + id + " is deleted"))
                .build();
    }
}
