package com.shop.storage.service.mapper;

import com.shop.storage.model.dto.PostBrandDto;
import com.shop.storage.model.dto.PostSouvenirDto;
import com.shop.storage.model.entity.Brand;
import com.shop.storage.model.entity.Souvenir;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SouvenirMapper {

    public PostBrandDto toDto(Brand entity) {
        return PostBrandDto.builder()
                           .name(entity.getName())
                           .country(entity.getCountry())
                           .build();
    }

    public Brand toEntity(PostBrandDto dto) {
        return Brand.builder()
                    .name(dto.getName())
                    .country(dto.getCountry())
                    .build();
    }

    public PostSouvenirDto toDto(Souvenir entity) {
        return PostSouvenirDto.builder()
                              .brand(toDto(entity.getBrand()))
                              .name(entity.getName())
                              .issue(entity.getIssue())
                              .price(entity.getPrice())
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
}
