package com.storage.dao.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public interface FileStorage<T> {

    Logger LOGGER = Logger.getLogger(FileStorage.class.getName());

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
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(file))) {
                if (header.equalsIgnoreCase(reader.readLine())) {
                    writeHeader = false;
                }
            } catch (IOException e) {
                LOGGER.warning("could not read csv file: " + filePath);
            }
        }
        return writeHeader;
    }
}
