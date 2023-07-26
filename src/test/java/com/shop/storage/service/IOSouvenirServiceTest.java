package com.shop.storage.service;

import com.shop.storage.dao.storage.BrandFileStorage;
import com.shop.storage.dao.storage.SouvenirFileStorage;
import com.shop.storage.model.dto.PostBrandDto;
import com.shop.storage.model.dto.PostSouvenirDto;
import com.shop.storage.model.entity.Brand;
import com.shop.storage.model.entity.Souvenir;
import com.shop.storage.service.exception.BrandNotFoundException;
import com.shop.storage.service.exception.SouvenirNotFoundException;
import com.shop.storage.service.facade.StorageFacade;
import com.shop.storage.service.mapper.SouvenirMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IOSouvenirServiceTest {

    @Test
    void findAllByBrand() {
    }

    @Test
    void findAllByYear() {
    }

    @Test
    void findAllByCountry() {
    }

    @Test
    void delete() {
    }

    private IOSouvenirService service;
    private StorageFacade facade;
    private SouvenirMapper mapper;
    private SouvenirFileStorage souvenirFileStorage;
    private BrandFileStorage brandFileStorage;

    @BeforeEach
    void setUp() {
        facade = mock(StorageFacade.class);
        mapper = mock(SouvenirMapper.class);
        service = new IOSouvenirService(facade, mapper);
        brandFileStorage = mock(BrandFileStorage.class);
        souvenirFileStorage = mock(SouvenirFileStorage.class);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given PostSouvenirDto, when save is called, then throws souvenir not found exception")
    void saveInvalidDtoThrowsSouvenirNotFoundException(
            String brandName, String brandCountry, LocalDateTime souvenirIssue, String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Brand brand = Brand.builder().name(brandName).country(brandCountry).build();
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
            String brandName, String brandCountry, LocalDateTime souvenirIssue, String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Brand brand = Brand.builder().name(brandName).country(brandCountry).build();
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
            String brandName, String brandCountry, LocalDateTime souvenirIssue, String souvenirName, double souvenirPrice) {
        PostBrandDto brandDto = getBuild(brandName, brandCountry);
        PostSouvenirDto dto = getPostSouvenirDto(souvenirIssue, souvenirName, souvenirPrice, brandDto);
        Brand brand = Brand.builder().name(brandName).country(brandCountry).build();
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
        PostBrandDto brandDto = PostBrandDto.builder().name(brandName).country(brandCountry).build();
        PostSouvenirDto dto = PostSouvenirDto.builder().brand(brandDto).issue(issue)
                                             .name(souvenirName).price(price).build();
        Souvenir souvenir = Souvenir.builder().brand(Brand.builder().name(brandName).country(brandCountry).build())
                                    .issue(issue).name(souvenirName).price(price).build();
        when(mapper.toEntity(dto)).thenReturn(souvenir);
        when(mapper.toDto(souvenir)).thenReturn(dto);
        when(facade.editSouvenir(souvenir)).thenReturn(souvenir);
        PostSouvenirDto result = service.edit(dto);
        verify(mapper).toEntity(dto);
        verify(mapper).toDto(souvenir);
        verify(facade).editSouvenir(souvenir);
        assertEquals(dto, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given findAll should return all souvenirs as PostSouvenirDto objects")
    void findAllReturnsAllSouvenirsAsDtos(
            String brandName, String brandCountry, LocalDateTime issue, String souvenirName, Double price) {
        Souvenir souvenir = Souvenir.builder().brand(Brand.builder().name(brandName).country(brandCountry).build())
                                    .issue(issue).name(souvenirName).price(price).build();
        List<Souvenir> souvenirs = List.of(souvenir);
        PostBrandDto brandDto = PostBrandDto.builder().name(brandName).country(brandCountry).build();
        PostSouvenirDto dto = PostSouvenirDto.builder().brand(brandDto).issue(issue)
                                             .name(souvenirName).price(price).build();
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
        Souvenir souvenir = Souvenir.builder().brand(Brand.builder().name(brandName).country(brandCountry).build())
                                    .issue(issue).name(souvenirName).price(price).build();
        List<Souvenir> souvenirs = List.of(souvenir);
        PostBrandDto brandDto = PostBrandDto.builder().name(brandName).country(brandCountry).build();
        PostSouvenirDto dto = PostSouvenirDto.builder().brand(brandDto).issue(issue)
                                             .name(souvenirName).price(price).build();
        List<PostSouvenirDto> expectedDtos = List.of(dto);

        when(facade.findAllSouvenirsByBrand(brandName)).thenReturn(souvenirs);
        when(mapper.toListDto(souvenirs)).thenReturn(expectedDtos);
        List<PostSouvenirDto> result = service.findAllByBrand(brandName);
        assertEquals(result, expectedDtos);
        verify(mapper).toListDto(souvenirs);
        verify(facade).findAllSouvenirsByBrand(brandName);
    }

    private static Souvenir getSouvenir(
            LocalDateTime souvenirIssue,
            String souvenirName,
            double souvenirPrice,
            Brand brand) {
        return Souvenir.builder()
                       .brand(brand)
                       .name(souvenirName)
                       .issue(souvenirIssue)
                       .price(souvenirPrice)
                       .build();
    }

    private static PostSouvenirDto getPostSouvenirDto(
            LocalDateTime souvenirIssue,
            String souvenirName,
            double souvenirPrice,
            PostBrandDto brandDto) {
        return PostSouvenirDto.builder()
                              .brand(brandDto)
                              .name(souvenirName)
                              .issue(souvenirIssue)
                              .price(souvenirPrice)
                              .build();
    }

    private static PostBrandDto getBuild(
            String brandName,
            String brandCountry) {
        return PostBrandDto.builder()
                           .name(brandName)
                           .country(brandCountry)
                           .build();
    }
}