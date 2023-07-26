package com.shop.storage.dao;

import java.util.List;
import java.util.Optional;

public interface FileStorage<T> {

    Optional<T> saveToCsv(T t);

    List<T> readFromCsv();
}
