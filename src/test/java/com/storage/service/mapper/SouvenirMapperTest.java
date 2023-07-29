package com.storage.service.mapper;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.model.entity.Brand;
import com.storage.model.entity.Souvenir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDateTime;
import java.util.List;

import static com.storage.service.Utils.getBrand;
import static com.storage.service.Utils.getBrandDto;
import static com.storage.service.Utils.getSouvenir;
import static com.storage.service.Utils.getSouvenirDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SouvenirMapperTest {
    private final BrandConverter brandConverter = new BrandConverter();
    private SouvenirMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new SouvenirMapper();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/brands.csv")
    @DisplayName("Test BrandConverter")
    void testBrandConverter(String country, String name) {
        String input = country + "," + name;
        Brand brand = (Brand) brandConverter.convert(input);
        assertNotNull(brand);
        System.out.println(brand);
        assertEquals(country, brand.getCountry());
        assertEquals(name, brand.getName());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Test PostSouvenirDto creation from CSV file")
    void testPostSouvenirDtoCreation(
            String brandName, String country, LocalDateTime issue,
            String souvenirName, double price) {
        Brand brand = getBrand(brandName, country);
        Souvenir souvenir = getSouvenir(brandName, country, issue, souvenirName, price);
        souvenir.setBrand(brand);
        PostSouvenirDto dto = mapper.toDto(souvenir);
        assertNotNull(dto);
        assertEquals(brandName, dto.getBrand()
                                   .getName());
        assertEquals(country, dto.getBrand()
                                 .getCountry());
        assertEquals(souvenirName, dto.getName());
        assertEquals(issue, dto.getIssue());
        assertEquals(price, dto.getPrice());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Test mapping from Brand entity to PostBrandDto")
    void testToDto(String brandName, String country) {

        Brand brand = getBrand(brandName, country);
        PostBrandDto dto = mapper.toDto(brand);
        assertNotNull(dto);
        assertEquals(brandName, dto.getName());
        assertEquals(country, dto.getCountry());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Test mapping from PostBrandDto to Brand entity")
    void testToEntity(String name, String country) {
        PostBrandDto dto = getBrandDto(name, country);
        Brand brand = mapper.toEntity(dto);
        assertNotNull(brand);
        assertEquals(name, brand.getName());
        assertEquals(country, brand.getCountry());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Test mapping from List of Souvenir entities to List of PostSouvenirDto")
    void testToListDto(
            String name, String country, LocalDateTime issue,
            String souvenirName, double price) {
        Souvenir souvenir = getSouvenir(name, country, issue, souvenirName, price);
        List<Souvenir> souvenirs = List.of(souvenir);
        List<PostSouvenirDto> dtos = mapper.toListDto(souvenirs);
        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        PostSouvenirDto dto = dtos.get(0);
        assertEquals(souvenirName, dto.getName());
        assertEquals(price, dto.getPrice());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Test mapping from PostSouvenirDto to Souvenir entity")
    void testToEntity(
            String name, String country, LocalDateTime issue,
            String souvenirName, double price) {
        PostBrandDto brandDto = getBrandDto(name, country);
        PostSouvenirDto dto = getSouvenirDto(issue, souvenirName, price, brandDto);
        Souvenir souvenir = mapper.toEntity(dto);
        assertNotNull(souvenir);
        assertEquals(souvenirName, souvenir.getName());
        assertEquals(price, souvenir.getPrice());
    }
}