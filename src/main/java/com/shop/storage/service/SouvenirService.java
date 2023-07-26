package com.shop.storage.service;

import java.util.List;

public interface SouvenirService<T> {

    T save(T t);

    T edit(T t);

    List<T> findAll();

    List<T> findAllByBrand(String name);

    List<T> findAllByYear(String year);

    List<T> findAllByCountry(String country);

    T delete(String name);
}
