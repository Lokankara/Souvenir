package com.storage.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SlimSouvenirDto extends Dto {
    @NonNull
    private String name;
    @NonNull
    private Double price;
    @NonNull
    private LocalDateTime issue;
}
