package com.storage.service.facade;

import com.storage.dao.BrandFileStorage;
import com.storage.dao.SouvenirFileStorage;
import com.storage.model.entity.Souvenir;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StorageFacade {
    private final BrandFileStorage brandStorage;
    private final SouvenirFileStorage souvenirStorage;
    private static final String SOUVENIR_PATH = "src/main/resources/data/souvenir.csv";

    public StorageFacade() {
        this.brandStorage = new BrandFileStorage();
        this.souvenirStorage = new SouvenirFileStorage();
    }

    public Souvenir saveSouvenir(
            final Souvenir souvenir) {
        souvenir.setBrand(brandStorage
                .saveToCsv(souvenir.getBrand()));
        return souvenirStorage.saveToCsv(souvenir);
    }

    public Souvenir editSouvenir(
            final Souvenir souvenir) {
        return souvenirStorage.updateCsv(souvenir);
    }

    public void delete(
            final String name) {
        souvenirStorage.deleteFromCsv(name);
    }

    public List<Souvenir> findAllSouvenirs() {
        return souvenirStorage.readFromCsv(SOUVENIR_PATH);
    }

    public List<Souvenir> findAllSouvenirsByBrand(
            final String name) {
        return souvenirStorage
                .readFromCsv(SOUVENIR_PATH)
                .stream()
                .filter(souvenir -> souvenir
                        .getBrand()
                        .getName()
                        .equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<Souvenir> findAllSouvenirsByYear(
            final String year) {
        return souvenirStorage
                .readFromCsv(SOUVENIR_PATH)
                .stream()
                .filter(souvenir -> String
                        .valueOf(souvenir
                                .getIssue()
                                .getYear())
                        .equals(year))
                .toList();
    }

    public List<Souvenir> findAllSouvenirsByCountry(
            final String country) {
        return souvenirStorage
                .readFromCsv(SOUVENIR_PATH)
                .stream()
                .filter(souvenir ->
                        souvenir.getBrand()
                                .getCountry()
                                .equalsIgnoreCase(country))
                .toList();
    }

    public List<Souvenir> getBrandsByLessPrice(
            final double price) {
        return souvenirStorage
                .readFromCsv(SOUVENIR_PATH)
                .stream()
                .filter(s -> s.getPrice() < price)
                .toList();
    }
}
