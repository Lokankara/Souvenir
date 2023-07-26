package com.shop.storage.dao.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileStorage<T> {

    Optional<T> saveToCsv(T t);

    List<T> readFromCsv();

    Optional<T> update(T t);

    Optional<T> deleteFromCsv(String name);

    default boolean isWriteHeader(
            String filePath,
            String header) {
        boolean writeHeader = true;
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {
                if (header.equalsIgnoreCase(reader.readLine())) {
                    writeHeader = false;
                }
            } catch (IOException e) {
                System.err.println("could not read csv file: " + filePath);
            }
        }
        return writeHeader;
    }
}
