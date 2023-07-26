package com.storage.model.entity;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Brand extends Entity {
    @NonNull
    @CsvBindByPosition(position = 0)
    private String name;
    @NonNull
    @CsvBindByPosition(position = 1)
    private String country;
}
