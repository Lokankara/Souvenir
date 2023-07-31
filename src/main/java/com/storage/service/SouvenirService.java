package com.storage.service;

import java.util.List;

public interface SouvenirService<T> {

    T save(T t);

    T update(T t);

    List<T> findAll();

    List<T> findAllByBrand(String name);

    List<T> findAllByYear(String year);

    List<T> findAllByCountry(String country);

    void delete(String name);
}
