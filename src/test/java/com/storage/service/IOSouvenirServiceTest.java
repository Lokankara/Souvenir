package com.storage.service;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.model.entity.Brand;
import com.storage.model.entity.Souvenir;
import com.storage.service.exception.BrandNotFoundException;
import com.storage.service.exception.DataInputException;
import com.storage.service.exception.SouvenirNotFoundException;
import com.storage.service.facade.StorageFacade;
import com.storage.service.mapper.SouvenirMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.storage.service.Utils.getBrand;
import static com.storage.service.Utils.getBrandDto;
import static com.storage.service.Utils.getBuild;
import static com.storage.service.Utils.getPostSouvenirDto;
import static com.storage.service.Utils.getSouvenir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IOSouvenirServiceTest {

    private IOSouvenirService service;
    private StorageFacade facade;
    private SouvenirMapper mapper;

    @BeforeEach
    void setUp() {
        facade = mock(StorageFacade.class);
        mapper = mock(SouvenirMapper.class);
        service = new IOSouvenirService(facade, mapper);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a name, when delete, then delete the souvenir with the given name")
    void deleteTest(String brandName, String brandCountry, LocalDateTime souvenirIssue,
                    String souvenirName, double souvenirPrice) {
        Brand brand = getBrand(brandName, brandCountry);
        Souvenir souvenir = getSouvenir(souvenirIssue, souvenirName, souvenirPrice, brand);
        doNothing().when(facade).deleteSouvenir(souvenir.getName());
        service.delete(souvenir.getName());
        verify(facade).deleteSouvenir(souvenir.getName());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a valid price, when findAllByPrice, then return all souvenirs with price less than or equal to the given price")
    void findAllByPriceTestValidPrice(String brandName, String brandCountry, LocalDateTime souvenirIssue,
                                      String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        Brand brand = getBrand(brandName, brandCountry);
        PostSouvenirDto expected = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Souvenir souvenir = getSouvenir(souvenirIssue, souvenirName, souvenirPrice, brand);
        when(facade.getBrandsByLessPrice(souvenirPrice)).thenReturn(Collections.singletonList(souvenir));
        when(mapper.toListDto(Collections.singletonList(souvenir))).thenReturn(Collections.singletonList(expected));
        List<PostSouvenirDto> result = service.findAllByPrice(String.valueOf(souvenirPrice));
        assertEquals(expected, result.get(0));
    }

    @Test
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given an invalid price, when findAllByPrice, then throw DataInputException")
    void findAllByPriceTestInvalidPrice() {
        String query = "invalid";
        assertThrows(DataInputException.class, () -> service.findAllByPrice(query));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a country, when findAllByCountry, then return all souvenirs from that country")
    void findAllByCountryTest(String brandName, String brandCountry, LocalDateTime souvenirIssue,
                              String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        PostSouvenirDto expected = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Brand brand = getBrand(brandName, brandCountry);
        Souvenir souvenir = getSouvenir(souvenirIssue, souvenirName, souvenirPrice, brand);
        when(facade.findAllSouvenirsByCountry(brandCountry)).thenReturn(Collections.singletonList(souvenir));
        when(mapper.toListDto(Collections.singletonList(souvenir))).thenReturn(Collections.singletonList(expected));
        List<PostSouvenirDto> result = service.findAllByCountry(brandCountry);
        assertEquals(expected, result.get(0));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given a year, when findAllByYear, then return all souvenirs from that year")
    void findAllByYearTest(String brandName, String brandCountry, LocalDateTime souvenirIssue,
                           String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        PostSouvenirDto expected = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Brand brand = getBrand(brandName, brandCountry);
        Souvenir souvenir = getSouvenir(souvenirIssue, souvenirName, souvenirPrice, brand);
        String year = String.valueOf(souvenirIssue.getYear());
        when(facade.findAllSouvenirsByYear(year)).thenReturn(Collections.singletonList(souvenir));
        when(mapper.toListDto(Collections.singletonList(souvenir))).thenReturn(Collections.singletonList(expected));
        List<PostSouvenirDto> result = service.findAllByYear(year);
        assertEquals(expected, result.get(0));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given PostSouvenirDto, when save is called, then throws souvenir not found exception")
    void saveInvalidDtoThrowsSouvenirNotFoundException(
            String brandName, String brandCountry, LocalDateTime souvenirIssue, String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Brand brand = getBrand(brandName, brandCountry);
        Souvenir souvenir = getSouvenir(souvenirIssue, souvenirName, souvenirPrice, brand);
        when(mapper.toEntity(dto)).thenReturn(souvenir);
        when(facade.saveSouvenir(souvenir)).thenThrow(new SouvenirNotFoundException(""));
        assertThrows(SouvenirNotFoundException.class, () -> service.save(dto));
        verify(mapper).toEntity(dto);
        verify(facade).saveSouvenir(souvenir);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    void saveInvalidBrandtThrowsBrandNotFoundException(
            String brandName, String brandCountry, LocalDateTime souvenirIssue,
            String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Brand brand = getBrand(brandName, brandCountry);
        Souvenir souvenir = getSouvenir(souvenirIssue, souvenirName, souvenirPrice, brand);
        when(mapper.toEntity(dto)).thenReturn(souvenir);
        when(facade.saveSouvenir(souvenir)).thenThrow(new BrandNotFoundException(""));
        assertThrows(BrandNotFoundException.class, () -> service.save(dto));
        verify(mapper).toEntity(dto);
        verify(facade).saveSouvenir(souvenir);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given valid souvenir data, save method should save and return the souvenir")
    void saveValidDtoSavesAndReturnsDto(
            String brandName, String brandCountry, LocalDateTime souvenirIssue,
            String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Brand brand = getBrand(brandName, brandCountry);
        Souvenir souvenir = getSouvenir(souvenirIssue, souvenirName, souvenirPrice, brand);
        when(mapper.toEntity(dto)).thenReturn(souvenir);
        when(facade.saveSouvenir(souvenir)).thenReturn(souvenir);
        when(mapper.toDto(souvenir)).thenReturn(dto);
        PostSouvenirDto result = service.save(dto);
        assertEquals(dto, result);
        verify(mapper).toEntity(dto);
        verify(facade).saveSouvenir(souvenir);
        verify(mapper).toDto(souvenir);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given PostSouvenirDto, when edit is called, method should save and return the updated souvenir")
    void editValidDtoSavesAndReturnsDto(
            String brandName, String brandCountry, String souvenirIssue, String souvenirName, String souvenirPrice) {
        LocalDateTime issue = LocalDateTime.parse(souvenirIssue);
        double price = Double.parseDouble(souvenirPrice);
        PostBrandDto brandDto = getBrandDto(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirName, issue, price, brandDto);
        Souvenir souvenir = getSouvenir(brandName, brandCountry, issue, souvenirName, price);
        when(mapper.toEntity(dto)).thenReturn(souvenir);
        when(mapper.toDto(souvenir)).thenReturn(dto);
        when(facade.updateSouvenir(souvenir)).thenReturn(souvenir);
        PostSouvenirDto result = service.update(dto);
        verify(mapper).toEntity(dto);
        verify(mapper).toDto(souvenir);
        verify(facade).updateSouvenir(souvenir);
        assertEquals(dto, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given findAll should return all souvenirs as PostSouvenirDto objects")
    void findAllReturnsAllSouvenirsAsDtos(
            String brandName, String brandCountry, LocalDateTime issue, String souvenirName, Double price) {
        Souvenir souvenir = getSouvenir(brandName, brandCountry, issue, souvenirName, price);
        List<Souvenir> souvenirs = List.of(souvenir);
        PostBrandDto brandDto = getBrandDto(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirName, issue, price, brandDto);
        List<PostSouvenirDto> expectedDtos = List.of(dto);
        when(facade.findAllSouvenirs()).thenReturn(souvenirs);
        when(mapper.toListDto(souvenirs)).thenReturn(expectedDtos);
        List<PostSouvenirDto> result = service.findAll();
        assertEquals(result, expectedDtos);
        verify(mapper).toListDto(souvenirs);
        verify(facade).findAllSouvenirs();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("findAllByBrand should return all souvenirs for the given brand name as PostSouvenirDto objects")
    void findAllByBrandReturnsAllSouvenirsForBrandAsDtos(
            String brandName, String brandCountry, LocalDateTime issue, String souvenirName, Double price) {
        Souvenir souvenir = getSouvenir(brandName, brandCountry, issue, souvenirName, price);
        List<Souvenir> souvenirs = List.of(souvenir);
        PostBrandDto brandDto = getBrandDto(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirName, issue, price, brandDto);
        List<PostSouvenirDto> expectedDtos = List.of(dto);
        when(facade.findAllSouvenirsByBrand(brandName)).thenReturn(souvenirs);
        when(mapper.toListDto(souvenirs)).thenReturn(expectedDtos);
        List<PostSouvenirDto> result = service.findAllByBrand(brandName);
        assertEquals(result, expectedDtos);
        verify(mapper).toListDto(souvenirs);
        verify(facade).findAllSouvenirsByBrand(brandName);
    }


}