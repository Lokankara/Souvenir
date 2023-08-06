package com.storage.service.facade;

import com.storage.dao.BrandFileStorage;
import com.storage.dao.SouvenirFileStorage;
import com.storage.model.entity.Brand;
import com.storage.model.entity.Souvenir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.storage.dao.BrandFileStorage.BRAND_PATH;
import static com.storage.dao.SouvenirFileStorageTest.SOUVENIR_PATH;
import static com.storage.service.Utils.getBrand;
import static com.storage.service.Utils.getSouvenir;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StorageFacadeTest {
    @Mock
    private BrandFileStorage brandStorage;
    @Mock
    private SouvenirFileStorage souvenirStorage;

    private StorageFacade storageFacade;

    private final List<Souvenir> souvenirs =
            new SouvenirFileStorage().readFromCsv(SOUVENIR_PATH);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storageFacade = new StorageFacade(brandStorage, souvenirStorage);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a brand, when saveToCsv, then return saved brand")
    void testSaveBrand(String brandCountry, String brandName) {
        Brand brand = getBrand(brandName, brandCountry);
        when(brandStorage.saveToCsv(brand)).thenReturn(brand);
        Brand result = storageFacade.saveBrand(brand);
        assertEquals(brand, result);
        verify(brandStorage).saveToCsv(brand);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a brand, when deleteToCsv, then return 204")
    void testDeleteBrand(String brandCountry, String name) {
        Brand brand = getBrand(name, brandCountry);
        storageFacade.deleteBrand(brand.getName());
        verify(souvenirStorage).deleteFromCsvByBrand(name);
        verify(brandStorage).deleteFromCsv(name);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a brand, when testFindAllBrands, then return list of brands")
    void testFindAllBrands(String brandCountry, String brandName) {
        Brand brand = getBrand(brandName, brandCountry);
        when(brandStorage.readFromCsv(BRAND_PATH))
                .thenReturn(Collections.singletonList(brand));
        List<Brand> result = storageFacade.findAllBrands();
        assertEquals(brand, result.get(0));
        verify(brandStorage).readFromCsv(BRAND_PATH);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a brand, when updateToCsv, then return update brand")
    void testUpdatedBrand(String brandCountry, String brandName) {
        Brand brand = getBrand(brandName, brandCountry);
        when(brandStorage.updateCsv(brand)).thenReturn(brand);
        Brand result = storageFacade.updateBrand(brand);
        assertEquals(brand, result);
        verify(brandStorage).updateCsv(brand);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when saved, then return the saved souvenir")
    void saveSouvenirTest(String brandName, String country, LocalDateTime issue,
                          String souvenirName, double price) {
        Brand brand = getBrand(brandName, country);
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.saveToCsv(souvenir)).thenReturn(souvenir);
        when(brandStorage.saveToCsv(brand)).thenReturn(brand);
        Souvenir savedSouvenir = storageFacade.saveSouvenir(souvenir);
        assertEquals(souvenir, savedSouvenir);
        assertEquals(souvenir.getBrand(), savedSouvenir.getBrand());
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
        when(souvenirStorage.updateCsv(souvenir)).thenReturn(souvenir);
        Souvenir updatedSouvenir = storageFacade.updateSouvenir(souvenir);
        assertEquals(souvenir, updatedSouvenir);
        assertEquals(brand, updatedSouvenir.getBrand());
        verify(souvenirStorage).updateCsv(souvenir);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when delete, then return the deleted souvenir")
    void deleteSouvenirTest(String brandName, String country, LocalDateTime issue,
                            String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        doNothing().when(souvenirStorage)
                   .deleteFromCsv(souvenirName);
        storageFacade.deleteSouvenir(souvenirName);
        verify(souvenirStorage).deleteFromCsv(souvenir.getName());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given souvenirs in storage, when findAllSouvenirs, then return all souvenirs")
    void findAllSouvenirsTest(String country, String brandName, LocalDateTime issue,
                              String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(storageFacade.findAllSouvenirs()).thenReturn(souvenirs);
        List<Souvenir> allSouvenirs = storageFacade.findAllSouvenirs();
        assertTrue(allSouvenirs.contains(souvenir));
    }

    @ParameterizedTest
    @DisplayName("Given souvenirs in storage and a brand name, when findAllSouvenirsByBrand, then return all souvenirs of that brand")
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    void findAllSouvenirsByBrandTest(String brandName, String country, LocalDateTime issue,
                                     String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        List<Souvenir> expectedSouvenirs = List.of(getSouvenir(brandName, country, issue, souvenirName, price));
        when(storageFacade.findAllSouvenirs()).thenReturn(souvenirs);
        when(storageFacade.findAllSouvenirsByBrand(brandName)).thenReturn(Collections.singletonList(souvenir));
        when(souvenirStorage.readFromCsv(SOUVENIR_PATH)).thenReturn(expectedSouvenirs);
        List<Souvenir> allSouvenirs = storageFacade.findAllSouvenirs();
        List<Souvenir> souvenirList = allSouvenirs
                .stream()
                .filter(s -> s.getBrand()
                              .getName()
                              .equalsIgnoreCase(brandName))
                .toList();
        List<Souvenir> actualSouvenirs = storageFacade.findAllSouvenirsByBrand(brandName);
        assertEquals(expectedSouvenirs, actualSouvenirs);
        assertEquals(souvenirList, actualSouvenirs);
    }

    @ParameterizedTest
    @DisplayName("Given souvenirs in storage and a year, when findAllSouvenirsByYear, then return all souvenirs from that year")
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    void findAllSouvenirsByYearTest(String brandName, String country, LocalDateTime issue,
                                    String souvenirName, double price) {
        int year = issue.getYear();
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        List<Souvenir> expectedSouvenirs = List.of(getSouvenir(brandName, country, issue, souvenirName, price));
        when(storageFacade.findAllSouvenirs()).thenReturn(souvenirs);
        when(storageFacade.findAllSouvenirsByYear(String.valueOf(year))).thenReturn(Collections.singletonList(souvenir));
        when(souvenirStorage.readFromCsv(SOUVENIR_PATH)).thenReturn(expectedSouvenirs);
        List<Souvenir> allSouvenirs = storageFacade.findAllSouvenirs();
        List<Souvenir> souvenirList = allSouvenirs
                .stream()
                .filter(s -> s.getIssue()
                              .getYear() == year)
                .toList();
        List<Souvenir> actualSouvenirs = storageFacade.findAllSouvenirsByYear(String.valueOf(year));
        assertEquals(expectedSouvenirs, actualSouvenirs);
        assertEquals(souvenirList, actualSouvenirs);
    }

    @ParameterizedTest
    @DisplayName("Given souvenirs in storage and a country, when findAllSouvenirsByCountry, then return all souvenirs from that country")
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    void findAllSouvenirsByCountryTest(String brandName, String country, LocalDateTime issue,
                                       String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        List<Souvenir> expectedSouvenirs = List.of(getSouvenir(brandName, country, issue, souvenirName, price));
        when(storageFacade.findAllSouvenirs()).thenReturn(souvenirs);
        when(storageFacade.findAllSouvenirsByCountry(country)).thenReturn(Collections.singletonList(souvenir));
        when(souvenirStorage.readFromCsv(SOUVENIR_PATH)).thenReturn(expectedSouvenirs);
        List<Souvenir> allSouvenirs = storageFacade.findAllSouvenirs();
        List<Souvenir> souvenirList = allSouvenirs
                .stream()
                .filter(s -> s.getBrand()
                              .getCountry()
                              .equalsIgnoreCase(country))
                .toList();
        List<Souvenir> actualSouvenirs = storageFacade.findAllSouvenirsByCountry(country);
        assertEquals(expectedSouvenirs, actualSouvenirs);
        assertEquals(souvenirList, actualSouvenirs);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given souvenirs in storage and a price, when getBrandsByLessPrice, then return all souvenirs less than the given price")
    void getBrandsByLessPriceTest(String brandName, String country, LocalDateTime issue,
                                  String souvenirName, double price) {
        List<Souvenir> souvenirs = List.of(getSouvenir(brandName, country, issue, souvenirName, price));
        when(souvenirStorage.readFromCsv(SOUVENIR_PATH)).thenReturn(souvenirs);
        List<Souvenir> souvenirsByLessPrice =
                storageFacade.getBrandsByLessPrice(price);
        assertEquals(souvenirs.stream()
                              .filter(s -> s.getPrice() < price)
                              .toList(), souvenirsByLessPrice);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when saveSouvenir, then souvenir saved")
    void saveSouvenirTestSouvenir(
            String brandName, String country, LocalDateTime issue,
            String souvenirName, double price) {
        Souvenir expected = new Souvenir();
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.saveToCsv(souvenir)).thenReturn(expected);
        Souvenir result = souvenirStorage.saveToCsv(souvenir);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when saveSouvenir, then if brand saved")
    void saveSouvenirTestBrand(
            String brandName, String country, LocalDateTime issue,
            String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.saveToCsv(souvenir)).thenReturn(souvenir);
        when(brandStorage.saveToCsv(souvenir.getBrand())).thenReturn(new Brand());
        Brand result = brandStorage.saveToCsv(souvenir.getBrand());
        assertNotNull(result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir, when editSouvenir, then souvenir updated")
    void editSouvenirTestSouvenir(String brandName, String country, LocalDateTime issue,
                                  String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        when(souvenirStorage.updateCsv(souvenir)).thenReturn(souvenir);
        Souvenir result = souvenirStorage.updateCsv(souvenir);
        assertEquals(souvenir, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a souvenir name, when delete, then souvenir deleted")
    void deleteTestSouvenir(String brandName, String country, LocalDateTime issue, String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        doNothing().when(souvenirStorage)
                   .deleteFromCsv(souvenir.getName());
        souvenirStorage.deleteFromCsv(souvenirName);
        verify(souvenirStorage).deleteFromCsv(souvenirName);
    }
}
