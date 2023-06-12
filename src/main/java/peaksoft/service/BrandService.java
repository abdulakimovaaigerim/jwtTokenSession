package peaksoft.service;

import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.response.BrandResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface BrandService {

    SimpleResponse saveBrand(BrandRequest brandRequest);

    BrandResponse getBrandById(Long id);

    List<BrandResponse> getAllBrands();

    SimpleResponse updateBrand(Long id, BrandRequest brandRequest);

    SimpleResponse deleteById(Long id);
}
