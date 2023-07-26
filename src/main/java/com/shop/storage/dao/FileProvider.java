package com.shop.storage.dao;

import com.shop.storage.model.entity.Brand;
import com.shop.storage.model.entity.Souvenir;

import java.util.List;
import java.util.Optional;

public interface FileProvider {
    Optional<Souvenir> saveToCsv(Souvenir souvenir);

    Optional<Brand> saveToCsv(Brand brand);

    List<Souvenir> readSouvenirsFromCsv();

    List<Brand> readBrandsFromCsv();

    Optional<Souvenir> deleteSouvenir(String name);
}
