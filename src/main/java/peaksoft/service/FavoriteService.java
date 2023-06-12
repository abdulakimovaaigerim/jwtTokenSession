package peaksoft.service;

import peaksoft.dto.request.FavoriteRequest;
import peaksoft.dto.response.FavoriteResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;


public interface FavoriteService {

    SimpleResponse saveFavorite(FavoriteRequest favoriteRequest);

    FavoriteResponse getFavoriteById(Long favoriteId);

    List<FavoriteResponse> getAllFavorite(Long productId, Long userId);

    SimpleResponse updateFavorite(Long favoriteId, FavoriteRequest favoriteRequest);

    SimpleResponse deleteFavorite(Long favoriteId);

   SimpleResponse addProductToFavorites(Long userId, Long productId);

   SimpleResponse removeProductFromFavorites(Long userId, Long productId);
}
