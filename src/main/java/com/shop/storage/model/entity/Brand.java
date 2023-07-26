package com.shop.storage.model.entity;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Brand
        extends Entity
        implements Serializable {

    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private String country;

    @Serial
    private static final long serialVersionUID = -1898255134063245L;

    @Serial
    private void writeObject(ObjectOutputStream out)
            throws IOException {
        out.writeUTF(getName());
        out.writeUTF(getCountry());
    }

    @Serial
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        setName(in.readUTF());
        setCountry(in.readUTF());
    }
}
