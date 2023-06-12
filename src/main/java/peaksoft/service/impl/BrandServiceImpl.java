package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.response.BrandResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entiti.Brand;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.BrandRepository;
import peaksoft.service.BrandService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public SimpleResponse saveBrand(BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setBrandName(brandRequest.brandName());
        brand.setImage(brandRequest.image());

        brandRepository.save(brand);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Brand with id: " + brand.getBrandName() + " is saved"))
                .build();
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        return brandRepository.getBrandById(id).orElseThrow(() ->
                new NotFoundException("Brand with id: " + id + " is not found! "));
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandRepository.getAllBrands();
    }

    @Override
    public SimpleResponse updateBrand(Long id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Brand with id: " + id + " is not found!"));
        brand.setBrandName(brandRequest.brandName());
        brand.setImage(brandRequest.image());
        brandRepository.save(brand);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Brand with id: " + brand.getBrandName() + " is updated"))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        brandRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Brand with id: " + id + " is deleted"))
                .build();
    }
}
