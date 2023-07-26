package com.shop.storage.service;

import com.shop.storage.model.entity.Brand;

import java.util.List;

public interface BrandService {

    void findBrandByPrice(double price);

    List<Brand> findAllBrandWithSouvenirs();

    List<Brand> findBrandsByYear(
            String souvenirName,
            String year);

    Brand deleteBrand(String manufacturerName);
}
