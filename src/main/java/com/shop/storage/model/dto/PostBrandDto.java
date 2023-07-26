package com.shop.storage.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostBrandDto extends Dto {

    private Long id;
    private String name;
    private String country;

    @JsonCreator
    public PostBrandDto(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("country") String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
