package com.storage.service;

import com.storage.dao.BrandFileStorage;
import com.storage.model.dto.PostBrandDto;
import com.storage.model.entity.Brand;
import com.storage.service.mapper.SouvenirMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IOBrandService implements BrandService<PostBrandDto> {

    private final SouvenirMapper mapper;
    private final BrandFileStorage brandStorage;
    private static final String BRAND_PATH = "src/main/resources/data/brand.csv";

    public IOBrandService() {
        this.brandStorage = new BrandFileStorage();
        this.mapper = new SouvenirMapper();
    }

    @Override
    public PostBrandDto save(
            PostBrandDto dto) {
        Brand brand = brandStorage.saveToCsv(
                mapper.toEntity(dto));
        return mapper.toDto(brand);

    }

    @Override
    public PostBrandDto edit(
            PostBrandDto dto) {
        Brand brand = brandStorage.updateCsv(
                mapper.toEntity(dto));
        return mapper.toDto(brand);
    }

    @Override
    public void delete(String name) {
        brandStorage.deleteFromCsv(name);
    }

    @Override
    public List<PostBrandDto> findAll() {
        return mapper.toListBrandDto(
                brandStorage.readFromCsv(BRAND_PATH));
    }

    @Override
    public List<PostBrandDto> findAllByCountry(
            String country) {
        return brandStorage
                .readFromCsv(BRAND_PATH)
                .stream()
                .filter(brand -> brand
                        .getCountry()
                        .equalsIgnoreCase(country))
                .map(mapper::toDto)
                .toList();
    }
}
