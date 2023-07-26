package com.storage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostSouvenirDto extends Dto {

    private Long id;
    @NonNull
    private String name;
    @NonNull
    private Double price;
    @NonNull
    private LocalDateTime issue;
    @NonNull
    private PostBrandDto brand;
}
