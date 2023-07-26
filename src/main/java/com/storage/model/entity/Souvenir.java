package com.storage.model.entity;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Souvenir extends Entity {
    @CsvRecurse
    private Brand brand;
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm")
    @CsvBindByPosition(position = 2)
    private LocalDateTime issue;
    @CsvBindByPosition(position = 3)
    private String name;
    @CsvBindByPosition(position = 4)
    private Double price;
}
