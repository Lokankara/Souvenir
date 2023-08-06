package com.storage.service;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.entity.Brand;
import com.storage.service.facade.StorageFacade;
import com.storage.service.mapper.SouvenirMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IOBrandService
        implements BrandService<PostBrandDto> {

    private final SouvenirMapper mapper;
    private final StorageFacade facade;

    public IOBrandService() {
        this.facade = new StorageFacade();
        this.mapper = new SouvenirMapper();
    }

    @Override
    public PostBrandDto save(
            final PostBrandDto dto) {
        Brand brand = facade.saveBrand(
                mapper.toEntity(dto));
        return mapper.toDto(brand);

    }

    @Override
    public PostBrandDto update(
            final PostBrandDto dto) {
        Brand brand = facade.updateBrand(
                mapper.toEntity(dto));
        return mapper.toDto(brand);
    }

    @Override
    public void delete(
            final String name) {
        facade.deleteBrand(name);
    }

    @Override
    public List<PostBrandDto> findAll() {
        return mapper.toListBrandDto(
                facade.findAllBrands());
    }

    @Override
    public List<PostBrandDto> findAllByCountry(
            final String country) {
        return facade
                .findAllBrands()
                .stream()
                .filter(brand -> brand
                        .getCountry()
                        .equalsIgnoreCase(country))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<PostBrandDto> findAllByName(
            final String name) {
        return facade
                .findAllBrands()
                .stream()
                .filter(brand -> brand
                        .getName()
                        .equalsIgnoreCase(name))
                .map(mapper::toDto)
                .toList();
    }
}
