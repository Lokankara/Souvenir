package com.storage.model.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostBrandDto extends Dto {

    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String country;
}
