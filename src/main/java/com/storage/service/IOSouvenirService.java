package com.storage.service;

import com.storage.model.dto.PostSouvenirDto;
import com.storage.model.entity.Souvenir;
import com.storage.service.exception.DataInputException;
import com.storage.service.facade.StorageFacade;
import com.storage.service.mapper.SouvenirMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IOSouvenirService
        implements SouvenirService<PostSouvenirDto> {
    private final StorageFacade facade;
    private final SouvenirMapper mapper;

    public IOSouvenirService() {
        this.facade = new StorageFacade();
        this.mapper = new SouvenirMapper();
    }

    @Override
    public PostSouvenirDto save(
            final PostSouvenirDto dto) {
        Souvenir souvenir = facade.saveSouvenir(
                mapper.toEntity(dto));
        return mapper.toDto(souvenir);
    }

    @Override
    public PostSouvenirDto update(
            final PostSouvenirDto dto) {
        Souvenir souvenir = facade.updateSouvenir(
                mapper.toEntity(dto));
        return mapper.toDto(souvenir);
    }

    @Override
    public List<PostSouvenirDto> findAll() {
        return mapper.toListDto(
                facade.findAllSouvenirs());
    }

    @Override
    public List<PostSouvenirDto> findAllByBrand(
            final String name) {
        return mapper.toListDto(
                facade.findAllSouvenirsByBrand(name));
    }

    @Override
    public List<PostSouvenirDto> findAllByYear(
            final String year) {
        return mapper.toListDto(
                facade.findAllSouvenirsByYear(year));
    }

    @Override
    public List<PostSouvenirDto> findAllByCountry(
            final String country) {
        return mapper.toListDto(
                facade.findAllSouvenirsByCountry(country));
    }

    @Override
    public void delete(final String name) {
        facade.deleteSouvenir(name);
    }

    public List<PostSouvenirDto> findAllByPrice(String query) {
        try {
            double price = Double.parseDouble(query);
            return mapper.toListDto(facade.getBrandsByLessPrice(price));
        } catch (Exception e) {
            throw new DataInputException("price invalid");
        }
    }
}
