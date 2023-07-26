package com.storage.service.facade;

import com.storage.dao.storage.BrandFileStorage;
import com.storage.dao.storage.SouvenirFileStorage;
import com.storage.model.entity.Brand;
import com.storage.model.entity.Souvenir;
import com.storage.service.exception.BrandNotFoundException;
import com.storage.service.exception.SouvenirNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StorageFacade {
    private final BrandFileStorage brandStorage;
    private final SouvenirFileStorage souvenirStorage;
    private static final String MESSAGE = "Entity not found: ";

    public StorageFacade() {
        this.brandStorage = new BrandFileStorage();
        this.souvenirStorage = new SouvenirFileStorage();
    }

    public Souvenir saveSouvenir(
            final Souvenir souvenir) {
        Souvenir saved = souvenirStorage
                .saveToCsv(souvenir)
                .orElseThrow(() ->
                        new SouvenirNotFoundException(
                                MESSAGE + souvenir.getName()));
        Brand brand = brandStorage
                .saveToCsv(souvenir.getBrand())
                .orElseThrow(() ->
                        new BrandNotFoundException(
                                MESSAGE + souvenir.getBrand().getName()));
        saved.setBrand(brand);
        return saved;
    }

    public Souvenir editSouvenir(
            final Souvenir souvenir) {
        return souvenirStorage
                .update(souvenir)
                .orElseThrow(() ->
                        new SouvenirNotFoundException(
                                MESSAGE + souvenir.getName()));
    }

    public Souvenir delete(String name) {
        return souvenirStorage
                .deleteFromCsv(name)
                .orElseThrow(() ->
                        new SouvenirNotFoundException(
                                MESSAGE + name));
    }

    public List<Souvenir> findAllSouvenirs() {
        return souvenirStorage.readFromCsv();
    }

    public List<Souvenir> findAllSouvenirsByBrand(
            final String name) {
        return souvenirStorage
                .readFromCsv()
                .stream()
                .filter(s -> s.getBrand().getName()
                              .equalsIgnoreCase(name))
                .toList();
    }

    public List<Souvenir> findAllSouvenirsByYear(
            final String year) {
        return souvenirStorage
                .readFromCsv()
                .stream()
                .filter(s -> String.valueOf(
                        s.getIssue().getYear()).equals(year))
                .toList();
    }

    public List<Souvenir> findAllSouvenirsByCountry(
            final String country) {
        return souvenirStorage
                .readFromCsv()
                .stream()
                .filter(s -> s.getBrand().getCountry()
                              .equalsIgnoreCase(country))
                .toList();
    }

    public List<Souvenir> getBrandsByLessPrice(double price) {
        return souvenirStorage
                .readFromCsv()
                .stream()
                .filter(s -> s.getPrice() < price)
                .toList();
    }
}