package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.FavoriteRequest;
import peaksoft.dto.response.FavoriteResponse;
import peaksoft.dto.response.SimpleResponse;

import peaksoft.entiti.Favorite;
import peaksoft.entiti.Product;
import peaksoft.entiti.User;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.FavoriteRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.FavoriteService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public SimpleResponse saveFavorite(FavoriteRequest favoriteRequest) {
        User user = userRepository.findById(favoriteRequest.getUserId()).orElseThrow(() ->
                new NotFoundException("User with id: " + favoriteRequest.getUserId() + " is not found!"));
        Product product = productRepository.findById(favoriteRequest.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id: " + favoriteRequest.getProductId() + " is not found!"));
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        favoriteRepository.save(favorite);


        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Favorite with id: " + favorite.getId() + " is saved"))
                .build();
    }

    @Override
    public FavoriteResponse getFavoriteById(Long favoriteId) {
        return favoriteRepository.getByFavoriteId(favoriteId).orElseThrow(() ->
                new NotFoundException("Favorite with id: " + favoriteId + " is not found!"));
    }

    @Override
    public List<FavoriteResponse> getAllFavorite(Long productId, Long userId) {
        return favoriteRepository.getAllFavorites(productId, userId);
    }

    @Override
    public SimpleResponse updateFavorite(Long favoriteId, FavoriteRequest favoriteRequest) {
        Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(() ->
                new NotFoundException("Favorite with id: " + favoriteId + " is not found!"));

        User user = userRepository.findById(favoriteRequest.getUserId()).orElseThrow(() ->
                new NotFoundException("User with id: " + favoriteRequest.getUserId() + " is not found!"));

        Product product = productRepository.findById(favoriteRequest.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id: " + favoriteRequest.getProductId() + " is not found!"));

        favorite.setUser(user);
        favorite.setProduct(product);
        favoriteRepository.save(favorite);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Favorite with id: " + favorite.getId() + " is updated"))
                .build();

    }

    @Override
    public SimpleResponse deleteFavorite(Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(() ->
                new NotFoundException("Favorite with id: " + favoriteId + " is not found!"));
        favorite.setProduct(null);
        favorite.setUser(null);
        favoriteRepository.delete(favorite);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Favorite with id : " + favoriteId + " is deleted"))
                .build();
    }

    @Override
    public SimpleResponse addProductToFavorites(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with id: " + userId + " is not found!"));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Product with id: " + productId + " is not found!"));
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);

        favoriteRepository.save(favorite);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Favorite with id: " + favorite.getId() + " is added!"))
                .build();
    }

    @Override
    public SimpleResponse removeProductFromFavorites(Long userId, Long productId) {
        favoriteRepository.findByUserIdAndProductId(userId, productId);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id: " + userId + "Product with id: " + productId + " is deleted"))
                .build();
    }

}
