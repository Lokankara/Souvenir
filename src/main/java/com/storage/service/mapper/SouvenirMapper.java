package com.storage.service.mapper;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.model.entity.Brand;
import com.storage.model.entity.Souvenir;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SouvenirMapper {

    public PostBrandDto toDto(Brand brand) {
        return PostBrandDto.builder()
                           .name(brand.getName())
                           .country(brand.getCountry())
                           .build();
    }

    public Brand toEntity(PostBrandDto dto) {
        return Brand.builder()
                    .name(dto.getName())
                    .country(dto.getCountry())
                    .build();
    }

    public PostSouvenirDto toDto(Souvenir souvenir) {
        return PostSouvenirDto.builder()
                              .brand(toDto(souvenir.getBrand()))
                              .name(souvenir.getName())
                              .issue(souvenir.getIssue())
                              .price(souvenir.getPrice())
                              .build();
    }

    public Souvenir toEntity(PostSouvenirDto dto) {
        return Souvenir.builder()
                       .brand(toEntity(dto.getBrand()))
                       .name(dto.getName())
                       .issue(dto.getIssue())
                       .price(dto.getPrice())
                       .build();
    }

    public List<PostSouvenirDto> toListDto(
            List<Souvenir> storage) {
        return storage
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<PostBrandDto> toListBrandDto(
            List<Brand> storage) {
        return storage
                .stream()
                .map(this::toDto)
                .toList();
    }
}
