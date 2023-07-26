package com.storage.service;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.model.entity.Brand;
import com.storage.model.entity.Souvenir;

import java.time.LocalDateTime;

public class Utils {
    public static Souvenir getSouvenir(
            String name, String country, LocalDateTime issue,
            String souvenirName, double price) {
        return Souvenir.builder()
                       .brand(Brand.builder()
                                   .name(name)
                                   .country(country)
                                   .build())
                       .name(souvenirName)
                       .issue(issue)
                       .price(price)
                       .build();
    }

    public static Brand getBrand(
            String brandName, String country) {
        return Brand.builder()
                    .name(brandName)
                    .country(country)
                    .build();
    }

    public static PostSouvenirDto getSouvenirDto(
            LocalDateTime issue, String souvenirName,
            double price, PostBrandDto brandDto) {
        return PostSouvenirDto.builder()
                              .brand(brandDto)
                              .name(souvenirName)
                              .issue(issue)
                              .price(price)
                              .build();
    }

    public static PostBrandDto getBrandDto(
            String name, String country) {
        return PostBrandDto.builder()
                           .name(name)
                           .country(country)
                           .build();
    }
}
