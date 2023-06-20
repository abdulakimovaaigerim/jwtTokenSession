package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entities.Category;
import peaksoft.entities.SubCategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<SubCategoryResponse> getAllByCategoryId(Long categoryId) {
        return subCategoryRepository.getAllByCategoryId(categoryId);
    }

    @Override
    public SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subCategoryRequest) {
       Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Category with id: " + categoryId + " is not found!"));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequest.name());
        category.addSubCategory(subCategory);
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with id: " + subCategoryRequest.name() + " is saved!"))
                .build();
    }

    @Override
    public SimpleResponse updateSubCategory(Long subCategoryId, SubCategoryRequest subCategoryRequest) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() ->
                new NotFoundException("SubCategory with id: " + subCategoryId + " is not found!"));
        subCategory.setName(subCategoryRequest.name());
        subCategoryRepository.save(subCategory);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with id: " + subCategoryRequest.name() + " is updated!"))
                .build();
    }

    @Override
    public SimpleResponse deleteSubCategoryById(Long subCategoryId) {
        if (!subCategoryRepository.existsById(subCategoryId)) {
            throw new NotFoundException("SubCategory with id: " + subCategoryId + " doesn't exists!");
        }
        subCategoryRepository.deleteById(subCategoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with id: " + subCategoryId + " is deleted!"))
                .build();
    }


    @Override
    public SubCategoryResponse findByIdSubCategory(Long subCategoryId) {
        return subCategoryRepository.getSubcategoryById(subCategoryId).orElseThrow(() ->
                new NotFoundException("SubCategory with id: " + subCategoryId + " is not found!"));
    }

    @Override
    public Map<String, List<SubCategoryResponse>> filterByCategory() {
        return subCategoryRepository.getAllSb().stream().collect(Collectors.groupingBy(SubCategoryResponse::categoryName));
    }


}
