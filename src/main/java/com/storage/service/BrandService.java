package com.storage.service;

import java.util.List;

public interface BrandService<T> {

    T save(T t);

    T edit(T t);

    List<T> findAll();

    List<T> findAllByCountry(String country);
    List<T> findAllByName(String name);

    void delete(String name);
}
