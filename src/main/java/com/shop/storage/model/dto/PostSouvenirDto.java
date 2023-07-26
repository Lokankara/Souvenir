package com.shop.storage.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostSouvenirDto extends Dto {

    private Long id;
    private String name;
    private Double price;
    private LocalDateTime issue;
    private PostBrandDto brand;

    @JsonCreator
    public PostSouvenirDto(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("name") LocalDateTime issue,
            @JsonProperty("brandDto") PostBrandDto brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.issue = issue;
        this.brand = brand;
    }
}
