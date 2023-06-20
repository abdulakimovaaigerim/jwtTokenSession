package peaksoft.service;

import org.springframework.data.domain.PageRequest;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.pagination.PaginationResponse;

import java.util.List;

public interface MenuItemService {

    SimpleResponse saveMenuItem(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest);

    List<MenuItemResponse> getAllMenuItems(Long subCategoryId, String word);

    MenuItemResponse getByIdMenuItem(Long menuItemId);

    SimpleResponse updateMenuItem(Long menuItemId, MenuItemRequest menuItemRequest);

    SimpleResponse deleteMenuItemById(Long menuItemId);

    List<MenuItemResponse> sortMenuItem(String ascOrDesc);

    List<MenuItemResponse> isVegetarian(boolean isTrue);

    PaginationResponse<MenuItemResponse> getMenuItemPagination(PageRequest pageRequest);
}
