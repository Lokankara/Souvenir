package com.shop.storage.model.entity;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serial;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Souvenir
        extends Entity
        implements Externalizable {
    @CsvRecurse
    private Brand brand;
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm")
    @CsvBindByPosition(position = 2)
    private LocalDateTime issue;
    @CsvBindByPosition(position = 3)
    private String name;
    @CsvBindByPosition(position = 4)
    private Double price;

    @Serial
    private static final long serialVersionUID = -18982551340645L;

    @Override
    public void writeExternal(ObjectOutput out)
            throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
    }
}
