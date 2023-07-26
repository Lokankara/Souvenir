package com.shop.storage.dao.io;

import com.shop.storage.dao.FileStorage;
import com.shop.storage.model.entity.Brand;
import com.shop.storage.model.entity.Souvenir;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SouvenirFileStorage
        implements FileStorage<Souvenir> {
    private final CsvProvider provider;

    public Optional<Souvenir> saveToCsv(Souvenir souvenir) {
        return provider.saveToCsv(souvenir);
    }

    public Optional<Brand> save(Brand brand) {
        return provider.saveToCsv(brand);
    }

    public Optional<Souvenir> findBy() {
        return Optional.empty();
    }

    public Optional<Souvenir> update(Souvenir souvenir) {
        return Optional.empty();
    }

    public List<Souvenir> readFromCsv() {
        return provider.readSouvenirsFromCsv();
    }

    public Optional<Souvenir> delete(String name) {
        return provider.deleteSouvenir(name);
    }
}
