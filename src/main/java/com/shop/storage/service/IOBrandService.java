package com.shop.storage.service;

import com.shop.storage.model.entity.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IOBrandService
        implements BrandService {
    @Override
    public void findBrandByPrice(double price) {

    }

    @Override
    public List<Brand> findAllBrandWithSouvenirs() {
        return null;
    }

    @Override
    public List<Brand> findBrandsByYear(
            String souvenirName,
            String year) {
        return null;
    }

    @Override
    public Brand deleteBrand(String manufacturerName) {
        return null;
    }

    public Brand getBrandById(Long id) {
        return new Brand();
    }

    public Brand getBrandName(String brand) {
        return Brand.builder().name(brand).build();
    }
}
