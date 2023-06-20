package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;

import java.util.List;
import java.util.Map;

public interface SubCategoryService {

    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);

    SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subCategoryRequest);

    SimpleResponse updateSubCategory(Long subCategoryId, SubCategoryRequest subCategoryRequest);

    SimpleResponse deleteSubCategoryById(Long subCategoryId);


    SubCategoryResponse findByIdSubCategory(Long subCategoryId);

    Map<String, List<SubCategoryResponse>> filterByCategory();

}
