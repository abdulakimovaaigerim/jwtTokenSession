package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoriesResponse;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entities.Category;
import peaksoft.exception.NotFoundException;
import peaksoft.pagination.PaginationResponse;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final SubCategoryRepository subCategoryRepository;
    @Override
    public List<CategoriesResponse> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with id: " + category.getName() + " is saved"))
                .build();
    }

    @Override
    public SimpleResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id: " + id + " is not found!"));
        category.setName(categoryRequest.name());
        categoryRepository.save(category);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with id: " + category.getName() + " is updated"))
                .build();
    }

    @Override
    public SimpleResponse deleteCategoryById(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category with id: " + categoryId + " doesn't exists!");
        }
        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Category with id: " + categoryId + " is deleted"))
                .build();
    }

    @Override
    public CategoryResponse categorySubCategories(Long categoryId) {
      Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Category with id: " + categoryId + " is not found!"));
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .subCategory(subCategoryRepository.getAllByCategoryId(categoryId))
                .build();
    }

    @Override
    public PaginationResponse<CategoriesResponse> getCategoryPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("name" ).descending());
        Page<CategoriesResponse> pageCategory = categoryRepository.findAllBy(pageable);
        return PaginationResponse.<CategoriesResponse>builder()
                .elements(pageCategory.getContent())
                .currentPage(pageable.getPageNumber()+1)
                .totalPage(pageCategory.getTotalPages())
                .build();
    }
}
