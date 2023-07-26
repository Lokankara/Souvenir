package com.storage.service;

import java.util.List;

public interface BrandService<T> {

    List<T> findAllByLessPrice(double price);

    List<T> findAllBrandWithSouvenirs();

    List<T> findBrandsByYear(
            String souvenirName,
            String year);

    T save(T t);

    T edit(T t);

    List<T> findAll();

    List<T> findAllByCountry(String country);

    T delete(String name);
}
