package com.shop.storage.service;

import com.shop.storage.dao.io.SouvenirFileStorage;
import com.shop.storage.model.dto.PostSouvenirDto;
import com.shop.storage.model.entity.Brand;
import com.shop.storage.model.entity.Souvenir;
import com.shop.storage.service.exception.SouvenirNotFoundException;
import com.shop.storage.service.mapper.SouvenirMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IOSouvenirService
        implements SouvenirService<PostSouvenirDto> {
    private final SouvenirMapper mapper;
    private final SouvenirFileStorage storage;

    @Override
    public PostSouvenirDto save(PostSouvenirDto dto) {
        Souvenir souvenir = storage.saveToCsv(
                mapper.toEntity(dto)).orElseThrow(() ->
                new SouvenirNotFoundException("Souvenir Not Found"));
        Brand brand = storage.save(
                mapper.toEntity(dto.getBrand())).orElseThrow(() ->
                new SouvenirNotFoundException("Souvenir Not Found"));
        return mapper.toDto(souvenir, brand);
    }

    @Override
    public PostSouvenirDto edit(PostSouvenirDto postSouvenirDto) {
        return null;
    }

    @Override
    public List<PostSouvenirDto> findAll() {
        return mapper.toListDto(storage.readFromCsv());
    }

    @Override
    public List<PostSouvenirDto> findAllByBrand(String name) {
        return null;
    }

    @Override
    public List<PostSouvenirDto> findAllByYear(String year) {
        return null;
    }

    @Override
    public List<PostSouvenirDto> findAllByCountry(String country) {
        return null;
    }

    @Override
    public PostSouvenirDto delete(String name) {
        return mapper.toDto(
                storage.delete(name).orElseThrow(() ->
                        new SouvenirNotFoundException("Souvenir Not Found")));
    }
}
