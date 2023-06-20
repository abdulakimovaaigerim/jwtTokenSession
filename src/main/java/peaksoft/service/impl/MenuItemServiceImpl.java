package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entities.MenuItem;
import peaksoft.entities.Restaurant;
import peaksoft.entities.SubCategory;
import peaksoft.exception.NotFoundException;
import peaksoft.pagination.PaginationResponse;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SimpleResponse saveMenuItem(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->
                new NotFoundException("Restaurant with id: " + restaurantId + " is not found!"));

        SubCategory subcategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() ->
                new NotFoundException("SubCategory with id: " + subCategoryId + " is not found!"));

        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Price can't be negative number!")
                    .build();
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setIsVegetarian(menuItemRequest.isVegetarian());

        menuItem.setRestaurant(restaurant);
        menuItem.setSubCategory(subcategory);
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("MenuItem with id: " + menuItem.getName() + " is saved!")
                .build();
    }

    @Override
    public List<MenuItemResponse> getAllMenuItems(Long subCategoryId, String word) {
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        if (word == null) {
            menuItemResponses.addAll(menuItemRepository.getAllMenuItems());
            return menuItemResponses;
        }
        menuItemResponses.addAll(menuItemRepository.globalSearch(word));
        return menuItemResponses;
    }

    @Override
    public MenuItemResponse getByIdMenuItem(Long menuItemId) {
        return menuItemRepository.getMenuItemById(menuItemId).orElseThrow(() ->
                new NotFoundException("MenuItem with id: " + menuItemId + " is not found!"));
    }

    @Override
    public SimpleResponse updateMenuItem(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                new NotFoundException("MenuItem with id: " + menuItemId + " is not found!"));
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setIsVegetarian(menuItemRequest.isVegetarian());
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("MenuItem with id: " + menuItem.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse deleteMenuItemById(Long menuItemId) {
        if (!menuItemRepository.existsById(menuItemId)) {
            throw new NotFoundException("MenuItem with id: " + menuItemId + " doesn't exists!");
        }
        menuItemRepository.deleteById(menuItemId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("MenuItem with id: " + menuItemId + " is deleted!")
                .build();
    }

    @Override
    public List<MenuItemResponse> sortMenuItem(String ascOrDesc) {
        if (ascOrDesc.equalsIgnoreCase("Asc")) {
            return menuItemRepository.sortByPriceAsc();
        } else if (ascOrDesc.equalsIgnoreCase("Desc")) {
            return menuItemRepository.sortByPriceDesc();
        } else {
            return menuItemRepository.getAllMenuItems();
        }
    }

    @Override
    public List<MenuItemResponse> isVegetarian(boolean isTrue) {
        return menuItemRepository.findMenuItemByIsVegetarian(isTrue);
    }

    @Override
    public PaginationResponse<MenuItemResponse> getMenuItemPagination(PageRequest pageRequest) {
        Page<MenuItemResponse> menuItemPage = menuItemRepository.pagination(pageRequest);


        return PaginationResponse.<MenuItemResponse>builder()
                .elements(menuItemPage.getContent())
                .currentPage(pageRequest.getPageNumber() + 1)
                .totalPage(menuItemPage.getTotalPages())
                .build();
    }

}
