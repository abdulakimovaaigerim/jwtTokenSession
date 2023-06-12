package peaksoft.service;


import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface BasketService {

    SimpleResponse saveBasket(BasketRequest basketRequest);

    BasketResponse getByBasketId(Long id);

    List<BasketResponse> getAllBaskets(Long userId);

    SimpleResponse updateBasket(Long id, BasketRequest basketRequest);

    SimpleResponse deleteById(Long id);

}
