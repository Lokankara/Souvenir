package com.storage.service;

import com.storage.dao.BrandFileStorage;
import com.storage.model.dto.PostBrandDto;
import com.storage.model.entity.Brand;
import com.storage.service.facade.StorageFacade;
import com.storage.service.mapper.SouvenirMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.storage.service.Utils.getBrand;
import static com.storage.service.Utils.getBrandDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IOBrandServiceTest {

    private static final String BRAND_PATH = "src/main/resources/data/brand.csv";
    private SouvenirMapper mapper;
    private BrandFileStorage brandStorage;
    private IOBrandService service;
    private StorageFacade facade;

    @BeforeEach
    void setUp() {
        facade = mock(StorageFacade.class);
        mapper = mock(SouvenirMapper.class);
        brandStorage = mock(BrandFileStorage.class);
        service = new IOBrandService(mapper, facade);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a name, when findAllByName, then return all brands with the given name")
    void findAllByNameTest(String brandCountry, String brandName) {
        PostBrandDto expected = getBrandDto(brandName, brandCountry);
        Brand brand = getBrand(brandName, brandCountry);
        List<Brand> brands = Collections.singletonList(brand);
        when(facade.findAllBrands()).thenReturn(brands);
//        when(brandStorage.readFromCsv(BRAND_PATH)).thenReturn(value);
        when(mapper.toDto(brand)).thenReturn(expected);
        List<PostBrandDto> result = service.findAllByName(brandName);
        assertEquals(expected, result.get(0));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a country, when findAllByCountry, then return all brands from that country")
    void findAllByCountryTest(String brandCountry, String brandName) {
        PostBrandDto expected = getBrandDto(brandName, brandCountry);
        Brand brand = getBrand(brandName, brandCountry);
        List<Brand> brands = Collections.singletonList(brand);
        when(facade.findAllBrands()).thenReturn(brands);
        when(mapper.toDto(brand)).thenReturn(expected);
        List<PostBrandDto> result = service.findAllByCountry(brandCountry);
        assertEquals(expected, result.get(0));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a name, when delete, then delete the brand with the given name")
    void deleteTest(String brandCountry, String brandName) {
        PostBrandDto dto = getBrandDto(brandName, brandCountry);
        Brand brand = getBrand(brandName, brandCountry);
        doNothing().when(facade).deleteBrand(brand.getName());
//        doNothing().when(brandStorage).deleteFromCsv(brand.getName());
        service.delete(dto.getName());
        verify(facade).deleteBrand(brandName);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a PostBrandDto, when edit, then update the brand and return the updated PostBrandDto")
    void editTest(String brandCountry, String brandName) {
        PostBrandDto dto = getBrandDto(brandName, brandCountry);
        Brand brand = getBrand(brandName, brandCountry);
//        when(brandStorage.updateCsv(mapper.toEntity(dto))).thenReturn(brand);
        when(mapper.toEntity(dto)).thenReturn(brand);
        when(facade.updateBrand(brand)).thenReturn(brand);
        when(mapper.toDto(brand)).thenReturn(dto);
        PostBrandDto result = service.update(dto);
        assertEquals(dto, result);
        verify(mapper).toEntity(dto);
        verify(facade).updateBrand(brand);
        verify(mapper).toDto(brand);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a PostBrandDto, when save, then save the brand and return the saved PostBrandDto")
    void saveTest(String brandCountry, String brandName) {
        PostBrandDto dto = getBrandDto(brandName, brandCountry);
        Brand brand = getBrand(brandName, brandCountry);
//        when(brandStorage.saveToCsv(mapper.toEntity(dto))).thenReturn(brand);
        when(mapper.toEntity(dto)).thenReturn(brand);
        when(facade.saveBrand(brand)).thenReturn(brand);
        when(mapper.toDto(brand)).thenReturn(dto);
        PostBrandDto result = service.save(dto);
        assertEquals(dto, result);
        verify(mapper).toEntity(dto);
        verify(facade).saveBrand(brand);
        verify(mapper).toDto(brand);
    }
}
