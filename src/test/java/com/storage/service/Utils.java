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
        Brand build = Brand
                .builder()
                .name(name)
                .country(country)
                .build();
        return Souvenir.builder()
                       .brand(build)
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

    public static Souvenir getSouvenir(
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

    public static PostSouvenirDto getPostSouvenirDto(
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

    public static PostBrandDto getBuild(
            String brandName,
            String brandCountry) {
        return getBrandDto(brandName, brandCountry);
    }

    public static PostSouvenirDto getPostSouvenirDto(
            String souvenirName, LocalDateTime issue,
            double price, PostBrandDto brandDto) {
        return PostSouvenirDto.builder()
                              .brand(brandDto)
                              .issue(issue)
                              .name(souvenirName)
                              .price(price)
                              .build();
    }

    public static Souvenir getSouvenir(
            String brandName, String brandCountry, LocalDateTime issue,
            String souvenirName, Double price) {
        return Souvenir.builder()
                       .brand(getBrand(brandName, brandCountry))
                       .issue(issue)
                       .name(souvenirName)
                       .price(price)
                       .build();
    }
}
