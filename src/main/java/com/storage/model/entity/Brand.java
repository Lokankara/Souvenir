package com.storage.model.entity;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Brand implements Entity {
    @NonNull
    @CsvBindByPosition(position = 0)
    private String name;
    @NonNull
    @CsvBindByPosition(position = 1)
    private String country;
}
