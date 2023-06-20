package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoriesResponse;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.pagination.PaginationResponse;

import java.util.List;

public interface CategoryService {

    List<CategoriesResponse> getAllCategories();

    SimpleResponse saveCategory(CategoryRequest categoryRequest);

    SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest);

    SimpleResponse deleteCategoryById(Long categoryId);

    CategoryResponse categorySubCategories(Long categoryId);
    PaginationResponse<CategoriesResponse> getCategoryPagination(int page, int size);

}
