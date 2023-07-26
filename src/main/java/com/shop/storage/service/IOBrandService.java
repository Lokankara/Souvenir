package com.shop.storage.service;

import com.shop.storage.dao.storage.BrandFileStorage;
import com.shop.storage.model.entity.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IOBrandService implements BrandService<Brand> {

    private final BrandFileStorage brandStorage;

    @Override
    public List<Brand> findAllByLessPrice(double price) {
        return null;
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
    public List<Brand> findAll() {
        return null;
    }

    @Override
    public List<Brand> findAllByCountry(String country) {
        return null;
    }

    @Override
    public Brand save(Brand brand) {
        return null;
    }

    @Override
    public Brand edit(Brand brand) {
        return null;
    }

    @Override
    public Brand delete(String name) {
        return null;
    }
}
