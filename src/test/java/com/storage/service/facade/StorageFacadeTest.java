package com.storage.service.facade;

import com.storage.dao.storage.BrandFileStorage;
import com.storage.dao.storage.SouvenirFileStorage;
import com.storage.model.entity.Brand;
import com.storage.model.entity.Souvenir;
import com.storage.service.exception.BrandNotFoundException;
import com.storage.service.exception.SouvenirNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.storage.service.Utils.getBrand;
import static com.storage.service.Utils.getSouvenir;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StorageFacadeTest {

    @Mock
    private BrandFileStorage brandStorage;
    @Mock
    private SouvenirFileStorage souvenirStorage;

    private StorageFacade storageFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storageFacade = new StorageFacade(brandStorage, souvenirStorage);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when saved, then return the saved souvenir")
    void saveSouvenirTest(String brandName, String country, LocalDateTime issue,
                          String souvenirName, double price) {
        Brand brand = getBrand(brandName, country);
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.saveToCsv(souvenir)).thenReturn(Optional.of(souvenir));
        when(brandStorage.saveToCsv(brand)).thenReturn(Optional.ofNullable(brand));
        Souvenir savedSouvenir = storageFacade.saveSouvenir(souvenir);
        assertEquals(souvenir.getName(), savedSouvenir.getName());
        assertEquals(souvenir.getPrice(), savedSouvenir.getPrice());
        assertEquals(souvenir.getBrand().getName(),
                savedSouvenir.getBrand().getName());
        assertEquals(souvenir.getBrand().getCountry(),
                savedSouvenir.getBrand().getCountry());
        assertEquals(souvenir.getIssue(), savedSouvenir.getIssue());
        verify(souvenirStorage).saveToCsv(souvenir);
        verify(brandStorage).saveToCsv(brand);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when updated, then return the updated souvenir")
    void updateSouvenirTest(String brandName, String country, LocalDateTime issue,
                            String souvenirName, double price) {
        Brand brand = getBrand(brandName, country);
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.update(souvenir)).thenReturn(Optional.ofNullable(souvenir));
        Souvenir updatedSouvenir = storageFacade.editSouvenir(souvenir);
        assertEquals(souvenir.getName(), updatedSouvenir.getName());
        assertEquals(souvenir.getPrice(), updatedSouvenir.getPrice());
        assertEquals(brand.getName(), updatedSouvenir.getBrand().getName());
        assertEquals(brand.getCountry(), updatedSouvenir.getBrand().getCountry());
        assertEquals(souvenir.getIssue(), updatedSouvenir.getIssue());
        verify(souvenirStorage).update(souvenir);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when delete, then return the deleted souvenir")
    void deleteSouvenirTest(String brandName, String country, LocalDateTime issue,
                            String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.deleteFromCsv(souvenirName)).thenReturn(Optional.of(souvenir));
        Souvenir deletedSouvenir = storageFacade.delete(souvenirName);
        assertEquals(souvenir, deletedSouvenir);
        verify(souvenirStorage).deleteFromCsv(souvenir.getName());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given souvenirs in storage, when findAllSouvenirs, then return all souvenirs")
    void findAllSouvenirsTest(String brandName, String country, LocalDateTime issue,
                              String souvenirName, double price) {
        List<Souvenir> souvenirs = List.of(getSouvenir(brandName, country, issue, souvenirName, price));
        when(souvenirStorage.readFromCsv()).thenReturn(souvenirs);
        List<Souvenir> allSouvenirs = storageFacade.findAllSouvenirs();
        assertEquals(souvenirs, allSouvenirs);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given souvenirs in storage and a brand name, when findAllSouvenirsByBrand, then return all souvenirs of that brand")
    void findAllSouvenirsByBrandTest(String brandName, String country, LocalDateTime issue,
                                     String souvenirName, double price) {
        List<Souvenir> souvenirs = List.of(getSouvenir(brandName, country, issue, souvenirName, price));
        when(souvenirStorage.readFromCsv()).thenReturn(souvenirs);
        List<Souvenir> allSouvenirsByBrand =
                storageFacade.findAllSouvenirsByBrand(brandName);
        assertEquals(souvenirs.stream()
                              .filter(s -> s.getBrand().getName()
                                            .equalsIgnoreCase(brandName))
                              .toList(), allSouvenirsByBrand);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given souvenirs in storage and a year, when findAllSouvenirsByYear, then return all souvenirs from that year")
    void findAllSouvenirsByYearTest(String brandName, String country, LocalDateTime issue,
                                    String souvenirName, double price) {
        List<Souvenir> souvenirs = List.of(getSouvenir(brandName, country, issue, souvenirName, price));

        when(souvenirStorage.readFromCsv()).thenReturn(souvenirs);
        List<Souvenir> allSouvenirsByYear =
                storageFacade.findAllSouvenirsByYear(String.valueOf(issue.getYear()));
        assertEquals(souvenirs.stream()
                              .filter(s -> String.valueOf(s.getIssue().getYear())
                                                 .equals(String.valueOf(issue.getYear())))
                              .toList(), allSouvenirsByYear);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given souvenirs in storage and a country, when findAllSouvenirsByCountry, then return all souvenirs from that country")
    void findAllSouvenirsByCountryTest(
            String brandName, String country, LocalDateTime issue,
            String souvenirName, double price) {
        List<Souvenir> souvenirs = List.of(
                getSouvenir(brandName, country, issue, souvenirName, price)
        );

        when(souvenirStorage.readFromCsv()).thenReturn(souvenirs);

        List<Souvenir> allSouvenirsByCountry =
                storageFacade.findAllSouvenirsByCountry(country);

        assertEquals(souvenirs.stream()
                              .filter(s -> s.getBrand().getCountry()
                                            .equalsIgnoreCase(country))
                              .toList(), allSouvenirsByCountry);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given souvenirs in storage and a price, when getBrandsByLessPrice, then return all souvenirs less than the given price")
    void getBrandsByLessPriceTest(String brandName, String country, LocalDateTime issue,
                                  String souvenirName, double price) {
        List<Souvenir> souvenirs = List.of(
                getSouvenir(brandName, country, issue, souvenirName, price)
        );
        when(souvenirStorage.readFromCsv()).thenReturn(souvenirs);

        List<Souvenir> souvenirsByLessPrice =
                storageFacade.getBrandsByLessPrice(price);
        assertEquals(souvenirs.stream()
                              .filter(s -> s.getPrice() < price)
                              .toList(), souvenirsByLessPrice);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when saveSouvenir, then throw SouvenirNotFoundException if souvenir not saved")
    void saveSouvenirTest_SouvenirNotFoundException(
            String brandName, String country, LocalDateTime issue,
            String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.saveToCsv(souvenir)).thenReturn(Optional.empty());
        assertThrows(SouvenirNotFoundException.class, () -> storageFacade.saveSouvenir(souvenir));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when saveSouvenir, then throw BrandNotFoundException if brand not saved")
    void saveSouvenirTest_BrandNotFoundException(
            String brandName, String country, LocalDateTime issue,
            String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.saveToCsv(souvenir)).thenReturn(Optional.of(souvenir));
        when(brandStorage.saveToCsv(souvenir.getBrand())).thenReturn(Optional.empty());
        assertThrows(BrandNotFoundException.class, () -> storageFacade.saveSouvenir(souvenir));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when editSouvenir, then throw SouvenirNotFoundException if souvenir not updated")
    void editSouvenirTest_SouvenirNotFoundException(String brandName, String country, LocalDateTime issue,
                                                    String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.update(souvenir)).thenReturn(Optional.empty());
        assertThrows(SouvenirNotFoundException.class, () -> storageFacade.editSouvenir(souvenir));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir name, when delete, then throw SouvenirNotFoundException if souvenir not deleted")
    void deleteTest_SouvenirNotFoundException(String brandName, String country, LocalDateTime issue,
                                              String souvenirName, double price) {
        when(souvenirStorage.deleteFromCsv(souvenirName)).thenReturn(Optional.empty());
        assertThrows(SouvenirNotFoundException.class, () -> storageFacade.delete(souvenirName));
    }
}
