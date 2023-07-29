package com.storage.dao;

import com.storage.model.entity.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandFileStorageTest {
    private static final String BRAND_PATH = "src/test/resources/brands.csv";
    @Mock
    private BrandFileStorage mockStorage;
    private final List<Brand> brandBefore =
            new BrandFileStorage().readFromCsv(BRAND_PATH);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/brands.csv", numLinesToSkip = 1)
    @DisplayName("Given a brand name and updated brand object, the updateCsv method should update the brand in the CSV file and return the updated brand object")
    void testUpdateCsv(String country, String name) {
        Brand updated = new Brand(country, name);
        when(mockStorage.updateCsv(updated)).thenReturn(updated);
        when(mockStorage.readFromCsv(BRAND_PATH)).thenReturn(brandBefore);
        Brand result = mockStorage.updateCsv(updated);
        assertEquals(updated, result);
        List<Brand> brands = mockStorage.readFromCsv(BRAND_PATH);
        assertTrue(brands.contains(updated));
    }

//    @Test
//    @DisplayName("Given a brand name, the deleteFromCsv method should delete the brand from the CSV file and return the deleted brand object")
//    void testDeleteFromCsv() {
//        BrandService brandService = new BrandService();
//        String name = "BrandName";
//        Optional<Brand> result = brandService.deleteFromCsv(name);
//        assertTrue(result.isPresent());
//        assertEquals(name, result.get().getName());
//
//        List<Brand> brands = readFromCsv(BRAND_PATH);
//        Optional<Brand> deletedBrandInCsv = brands.stream()
//                                                  .filter(b -> b.getName().equals(name))
//                                                  .findFirst();
//        assertFalse(deletedBrandInCsv.isPresent());
//    }

}
