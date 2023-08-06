package com.storage.model.entity;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Souvenir implements Entity {
    @Serial
    private static final long serialVersionUID = 7829136421241571165L;
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
